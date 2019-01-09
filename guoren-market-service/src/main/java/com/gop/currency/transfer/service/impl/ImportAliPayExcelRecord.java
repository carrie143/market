package com.gop.currency.transfer.service.impl;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import com.google.common.base.Strings;
import com.gop.currency.transfer.service.ImportExcelRecord;
import com.gop.domain.ChannelAlipayOrderDeposit;
import com.gop.domain.DepositMatchBankOrderUser;
import com.gop.exception.ExcelRowException;
import com.gop.mapper.ChannelAlipayOrderDepositMapper;
import com.gop.mapper.DepositMatchBankOrderUserMapper;
import com.gop.mode.vo.ImportExcelResultVo;
import com.gop.util.BigDecimalUtils;
import com.gop.util.DateTimeUtil;
import com.gop.util.ExcelUtil;
import com.gop.util.TransactionUtil;

import lombok.extern.slf4j.Slf4j;

@Service("aliPay")
@Slf4j
public class ImportAliPayExcelRecord implements ImportExcelRecord {

	@Autowired
	private DepositMatchBankOrderUserMapper depositMatchBankOrderUserMapper;

	@Autowired
	private ChannelAlipayOrderDepositMapper alipayOrderDepositMapper;

	@Autowired
	TransactionUtil transactionUtil;

	@Override
	public ImportExcelResultVo importRecordExcel(File file, String fileName) {
		throw new IllegalArgumentException("程序没开发好");
	}

	@Override
	public ImportExcelResultVo importRecordExcel(InputStream is, String fileName, Integer uid) {
		XSSFWorkbook workBook = null;
		ImportExcelResultVo resultVo = new ImportExcelResultVo();
		int failCount = 0;
		int successCount = 0;
		try {
			workBook = ExcelUtil.createWorkBook(is);
			List<List<String>> values = ExcelUtil.getSheetValues(workBook, 0);
			String accountName = getAliPayAccountName(values.get(0));
			for (int i = 3; i < values.size() - 1; i++) {
				ChannelAlipayOrderDeposit alipayPayRecord = null;
				DepositMatchBankOrderUser bankStatement = null;
				try {
					alipayPayRecord = mapList2Object(values.get(i));
					alipayPayRecord.setAccountNo(accountName);
					alipayPayRecord.setFileName(fileName);
					bankStatement = AlipayPayRecord2BankStatement(alipayPayRecord);
					bankStatement.setEditerAdminId(uid);
				} catch (ExcelRowException e) {
					resultVo.addPair((i + 1), e.getColumn(), e.getErrorMsg());
					failCount++;
					continue;
				}
				TransactionStatus ts = null;
				try {
					ts = transactionUtil.beginRequiredTransactional();
					depositMatchBankOrderUserMapper.insertSelective(bankStatement);
					alipayOrderDepositMapper.insertSelective(alipayPayRecord);
					transactionUtil.commit(ts);
					successCount++;
				} catch (DuplicateKeyException e) {
					resultVo.addPair((i + 1), null, "重复的账单导入");
					transactionUtil.rollback(ts);
					failCount++;
				} catch (Exception e) {
					resultVo.addPair((i + 1), null, "账单导入数据库失败");
					log.error("导入数据:" + alipayPayRecord + "失败", e);
					transactionUtil.rollback(ts);
					failCount++;
				}
			}
		} catch (Exception e) {
			log.error("解析excel失败:", e);
			resultVo.setSuccessCount(0);
			resultVo.setErroMessage("解析excel失败");
		}
		resultVo.setSuccessCount(successCount);
		resultVo.setFailCount(failCount);
		return resultVo;
	}

	private DepositMatchBankOrderUser AlipayPayRecord2BankStatement(ChannelAlipayOrderDeposit alipayPayRecord) {
		DepositMatchBankOrderUser bankStatement = new DepositMatchBankOrderUser();
		bankStatement.setAccountNo(alipayPayRecord.getReciprocalAccount());
		bankStatement.setBankName("支付宝");
		bankStatement.setCreateDate(alipayPayRecord.getRecordedDate());
		bankStatement.setSource("支付宝");
		bankStatement.setSerialNumber(alipayPayRecord.getOrderNo());
		bankStatement.setMoney(new BigDecimal(alipayPayRecord.getIncome()));
		bankStatement.setName(alipayPayRecord.getReciprocalName());
		bankStatement.setPostscript(alipayPayRecord.getRemark());
		bankStatement.setName(alipayPayRecord.getReciprocalName());
		return bankStatement;
	}

	private String getAliPayAccountName(List<String> list) {
		if (list == null || list.size() < 1 || Strings.isNullOrEmpty(list.get(0)))
			throw new IllegalArgumentException("无法获取账单对应支付宝账号");
		String account = list.get(0);
		int index = account.lastIndexOf("：");
		if (index + 1 >= account.length()) {
			return account;
		} else {
			return account.substring(account.lastIndexOf("：") + 1);
		}
	}

	private ChannelAlipayOrderDeposit mapList2Object(List<String> list) {
		ChannelAlipayOrderDeposit alipayPayRecord = new ChannelAlipayOrderDeposit();

		if (list.size() < 17)
			throw new ExcelRowException(0, "列数不对实际为：" + list.size() + "列");
		String dateString = list.get(1);
		Date date = null;
		try {
			date = DateTimeUtil.getFormatDate(DateTimeUtil.getFormatString(dateString), dateString);
		} catch (Exception e) {
			throw new ExcelRowException(2, "时间格式不正确:"+dateString);
		}
		alipayPayRecord.setRecordedDate(date);
		alipayPayRecord.setTransactionNo(list.get(2));
		alipayPayRecord.setOrderNo(list.get(3));
		alipayPayRecord.setBusinessNo(list.get(4));
		alipayPayRecord.setTransferType(list.get(5));

		if (!BigDecimalUtils.isValidMoneyAmount(list.get(6))) {
			throw new ExcelRowException(7, "收入列：金额错误:"+list.get(6));
		}
		alipayPayRecord.setIncome(list.get(6));

		if (!Strings.isNullOrEmpty(list.get(7))) {

			if (!BigDecimalUtils.isValidMoneyAmount(list.get(7))) {
				throw new ExcelRowException(8, "支出列：金额错误:"+list.get(7));
			}
			alipayPayRecord.setExpenditure(list.get(7));
		}

		if (!BigDecimalUtils.isValidMoneyAmount(list.get(8))) {
			throw new ExcelRowException(9, "账户余额列：金额错误:"+list.get(8));
		}
		alipayPayRecord.setAccountBalance(list.get(8));

		if (!BigDecimalUtils.isValidMoneyAmount(list.get(9))) {
			throw new ExcelRowException(10, "服务费列：金额错误:"+list.get(9));
		}
		alipayPayRecord.setAccountBalance(list.get(9));

		alipayPayRecord.setPaymentChannels(list.get(10));

		alipayPayRecord.setContractProduct(list.get(11));

		alipayPayRecord.setReciprocalAccount(list.get(12));
		alipayPayRecord.setReciprocalName(list.get(13));
		alipayPayRecord.setBankOrder(list.get(14));
		alipayPayRecord.setProductName(list.get(15));
		alipayPayRecord.setRemark(list.get(16));
		return alipayPayRecord;

	}

//	public static void main(String[] args) throws IOException {
//		ImportAliPayExcelRecord a = new ImportAliPayExcelRecord();
//		File f = new File("C:\\Users\\Administrator\\Desktop\\1.xlsx");
//		XSSFWorkbook workBook = ExcelUtil.createWorkBook(new FileInputStream(f));
//		System.out.println(ExcelUtil.getSheetValues(workBook, 0));
//
//	}

}
