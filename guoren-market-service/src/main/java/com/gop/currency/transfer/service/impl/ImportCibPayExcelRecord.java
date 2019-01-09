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

import com.gop.currency.transfer.service.ImportExcelRecord;
import com.gop.domain.ChannelCibOrderDeposit;
import com.gop.domain.DepositMatchBankOrderUser;
import com.gop.exception.ExcelRowException;
import com.gop.mapper.ChannelCibOrderDepositMapper;
import com.gop.mapper.DepositMatchBankOrderUserMapper;
import com.gop.mode.vo.ImportExcelResultVo;
import com.gop.util.BigDecimalUtils;
import com.gop.util.DateTimeUtil;
import com.gop.util.ExcelUtil;
import com.gop.util.TransactionUtil;

import lombok.extern.slf4j.Slf4j;

@Service("cibPay")
@Slf4j
public class ImportCibPayExcelRecord implements ImportExcelRecord {

	@Autowired
	private DepositMatchBankOrderUserMapper depositMatchBankOrderUserMapper;

	@Autowired
	private ChannelCibOrderDepositMapper cibPayRecordMapper;

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
			for (int i = 1; i < values.size(); i++) {
				ChannelCibOrderDeposit cibPayRecord = null;
				DepositMatchBankOrderUser bankStatement = null;
				try {
					cibPayRecord = mapList2Object(values.get(i));
					cibPayRecord.setFileName(fileName);
					bankStatement = cibPayRecord2BankStatement(cibPayRecord);
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
					cibPayRecordMapper.insertSelective(cibPayRecord);
					transactionUtil.commit(ts);
					successCount++;
				} catch (DuplicateKeyException e) {
					resultVo.addPair((i + 1), null, "重复的账单导入");
					transactionUtil.rollback(ts);
					failCount++;
				} catch (Exception e) {
					resultVo.addPair((i + 1), null, "账单导入数据库失败");
					log.error("导入数据:" + cibPayRecord + "失败", e);
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

	@SuppressWarnings("unused")
	private DepositMatchBankOrderUser cibPayRecord2BankStatement(ChannelCibOrderDeposit CibPayRecord) {
		DepositMatchBankOrderUser bankStatement = new DepositMatchBankOrderUser();
		bankStatement.setAccountNo(CibPayRecord.getReciprocalAccount());
		bankStatement.setSerialNumber(CibPayRecord.getTransactionNo());
		bankStatement.setBankName(CibPayRecord.getReciprocalBank());
		bankStatement.setName(CibPayRecord.getReciprocalName());
		bankStatement.setCreateDate(CibPayRecord.getTransactionDate());
		bankStatement.setMoney(new BigDecimal(CibPayRecord.getDebitAmount()));
		bankStatement.setPostscript(CibPayRecord.getRemark());
		bankStatement.setSource("兴业银行");
		return bankStatement;
	}

	@SuppressWarnings("unused")
	private ChannelCibOrderDeposit mapList2Object(List<String> list) {
		ChannelCibOrderDeposit CibPayRecord = new ChannelCibOrderDeposit();
		if (list.size() < 16)
			throw new ExcelRowException(null, "列数不对实际为：" + list.size() + "列");

		CibPayRecord.setTransactionNo(list.get(0));
		CibPayRecord.setAccountNo(list.get(1));
		CibPayRecord.setAccountName(list.get(2));
		CibPayRecord.setVoucherNo(list.get(3));
		CibPayRecord.setCurrency(list.get(4));
		CibPayRecord.setType(list.get(5));
		if (!BigDecimalUtils.isValidMoneyAmount(list.get(7))) {
			throw new ExcelRowException(6, "收入列：金额错误读取到的值为:"+list.get(7));
		}
		CibPayRecord.setDebitAmount(list.get(7));
		CibPayRecord.setCreditAmount(list.get(6));
		CibPayRecord.setAccountBalance(list.get(8));
		CibPayRecord.setAbstractMsg(list.get(9));
		CibPayRecord.setReciprocalAccount(list.get(10));
		CibPayRecord.setReciprocalName(list.get(11));
		CibPayRecord.setReciprocalBank(list.get(12));
		CibPayRecord.setReciproalBankingFirm(list.get(13));
		
		String dateString = list.get(14);
		Date date = null;
		try {
			date = DateTimeUtil.getFormatDate(DateTimeUtil.getFormatString(dateString), dateString);
		} catch (Exception e) {
			throw new ExcelRowException(15, "时间格式不正确读取到的值为:"+list.get(14));
		}

		CibPayRecord.setTransactionDate(date);
		CibPayRecord.setRemark(list.get(15));
		return CibPayRecord;

	}

}
