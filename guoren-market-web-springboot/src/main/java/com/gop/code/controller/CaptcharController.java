package com.gop.code.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gop.code.consts.IdentifyingCodeConst;
import com.gop.common.IdentifyingCodeService;
import com.gop.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/captchar")
@Slf4j
public class CaptcharController {

	@Autowired
	@Qualifier("IdentifyingCodeServiceImpl")
	private IdentifyingCodeService identifyingCodeServiceImpl;

	public static final long CAPTCHAR_EXPIRE_TIME = 600000L;

	private int width = 90;// 定义图片的width
	private int height = 38;// 定义图片的height
	private int codeCount = 4;// 定义图片上显示验证码的个数
	private int xx = 15;
	private int fontHeight = 20;
	private int codeY = 26;
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	@RequestMapping(value = "/code", method = RequestMethod.GET)
	@ResponseBody
	public void getCode(@RequestParam("randomKey") String randomKey, HttpServletRequest req, HttpServletResponse resp) {
		ServletOutputStream sos = null;
		try {

			String randomCode = RandomStringUtils.randomNumeric(4);

			Boolean saveCode = identifyingCodeServiceImpl.saveCode(randomCode.toString(), randomKey,
					CAPTCHAR_EXPIRE_TIME, 0L);

			log.info("生产的验证吗是:{}", randomCode);
			String code = identifyingCodeServiceImpl.getCode(randomKey);
			log.info("获取的验证吗是:{}", randomCode);
			CreateImageCode vCode = new CreateImageCode(randomCode.toString());

			// 禁止图像缓存。
			resp.setHeader("Pragma", "no-cache");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setDateHeader("Expires", 0);
			resp.setContentType("image/jpeg");
			sos = resp.getOutputStream();
			vCode.write(sos);

		} catch (Exception e) {
			log.error("发送登录验证码异常", e);
		} finally {
			if (sos != null) {
				try {
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 验证图形验证吗（前端调用）
	// 校验验证码

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public void validCaptharCode(@RequestParam("code") String code, @RequestParam("randomKey") String randomKey) {

		String captharCode = identifyingCodeServiceImpl.getCode(randomKey);

		boolean isValid = code.equalsIgnoreCase(captharCode);
		log.info("redis中获取到的验证码是:{}，用户输入的验证码是:{}", captharCode, code);

		if (!isValid) {
			throw new AppException(IdentifyingCodeConst.GRAPHICAL_IDENTIFYING_CODE_EEROR);
		}

	}
}
