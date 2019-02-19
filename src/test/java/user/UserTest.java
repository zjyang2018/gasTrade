/**  
 * Project Name:xiniu-core-web  
 * File Name:UserMsgTest.java  
 * Package Name:com.xiniu.online.user.test  
 * Date:2017年3月1日上午11:05:05  
 * Copyright (c) 2017, 汇联基金 All Rights Reserved.  
 *  
 */

package test.java.user;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.common.cache.CacheService;
import com.common.exception.CommonException;
import com.sun.istack.internal.logging.Logger;


/**
 * ClassName:LotteryTest <br/>
 * Description: TODO 添加描述. <br/>
 * Date: 2017年3月1日 上午11:05:05 <br/>
 * 
 * @author zhangzhicheng
 * @version
 * @since JDK 1.7
 * @see
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:resources/spring/applicationContext.xml" })
public class UserTest {

	Logger logger = Logger.getLogger(UserTest.class);

//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private CacheService cacheService;
//
//	@Autowired
//	private UploadRealTimeDataMapper uploadRealTimeDataMapper;
//
//	@Autowired
//	private UploadMonitorInfoService uploadMonitorInfoService;

	/**
	 * testUser:(测试). <br/>
	 * 
	 * @author zhangzhicheng
	 * @throws CommonException
	 * @since JDK 1.7
	 */
//	@Test
//	public void testUser() {
//		try {
//			System.out.println("==");
//			UserVo userVo = new UserVo();
//			userVo.setPage(1);
//			userVo.setPageSize(20);
//
//			// List<UserVo> list = userService.getUserList(userVo);
//			List<UserVo> list = userService.getUserPage(userVo);
//			System.out.println(list.toString());
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
//
//	@Test
//	public void testCatch() {
//		try {
//			// cacheService.add("123456", "123456 content");
//
//			Integer content = cacheService.get("lock_xxx");
//			if (content == null) {
//				content = 0;
//			}
//			System.out.println(content + 1);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
//
//	@Test
//	public void testUploadRealTimeData() {
//		try {
//			UploadRealTimeDataVo uploadRealTimeDataVo = new UploadRealTimeDataVo();
//			List<UploadRealTimeDataVo> list = uploadRealTimeDataMapper.getUploadRealTimeDataList(uploadRealTimeDataVo);
//			System.out.println(list.toString());
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
//
//	@Test
//	public void testQueryUploadRealTimeData() {
//		try {
//			// 201803290001
//			List<MonitorRealTimeDto> list = uploadMonitorInfoService.queryMonitorRealTimeData("201803290001");
//			System.out.println(list.toString());
//
//		} catch (Exception e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//	}

}
