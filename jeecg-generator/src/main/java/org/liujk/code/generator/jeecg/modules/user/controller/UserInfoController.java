package org.liujk.code.generator.jeecg.modules.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.liujk.code.generator.jeecg.common.api.CommonResultCode;
import org.liujk.code.generator.jeecg.common.api.DefaultResponse;
import org.liujk.code.generator.jeecg.modules.system.query.QueryGenerator;
import org.liujk.code.generator.jeecg.common.util.oConvertUtils;
import org.liujk.code.generator.jeecg.modules.user.entity.UserInfo;
import org.liujk.code.generator.jeecg.modules.user.service.IUserInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;

 /**
 * @Title: Controller
 * @Description: user_info
 * @author： jeecg-boot
 * @date：   2020-12-03
 * @version： V1.0
 */
@RestController
@RequestMapping("/user/userInfo")
@Slf4j
public class UserInfoController {
	@Autowired
	private IUserInfoService userInfoService;
	
	/**
	  * 分页列表查询
	 * @param userInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	@ApiOperation("分页列表查询")
	public DefaultResponse<IPage<UserInfo>> queryPageList(UserInfo userInfo,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		DefaultResponse<IPage<UserInfo>> result = new DefaultResponse<IPage<UserInfo>>();
		QueryWrapper<UserInfo> queryWrapper = QueryGenerator.initQueryWrapper(userInfo, req.getParameterMap());
		Page<UserInfo> page = new Page<UserInfo>(pageNo, pageSize);
		IPage<UserInfo> pageList = userInfoService.page(page, queryWrapper);
		result.setData(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param userInfo
	 * @return
	 */
	@PostMapping(value = "/add")
	@ApiOperation("添加")
	public DefaultResponse<UserInfo> add(@RequestBody UserInfo userInfo) {
		DefaultResponse<UserInfo> result = new DefaultResponse<UserInfo>();
		try {
			userInfoService.save(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result.setResultCode(CommonResultCode.UNKNOWN_EXCEPTION.getCode());
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param userInfo
	 * @return
	 */
	@PutMapping(value = "/edit")
	@ApiOperation("编辑")
	public DefaultResponse<UserInfo> edit(@RequestBody UserInfo userInfo) {
		DefaultResponse<UserInfo> result = new DefaultResponse<UserInfo>();
		UserInfo userInfoEntity = userInfoService.getById(userInfo.getId());
		if(userInfoEntity==null) {
			result.setResultCode(CommonResultCode.DATA_NOT_EXISTS.getCode());
		}else {
			boolean ok = userInfoService.updateById(userInfo);
			if(!ok) {
				result.setResultCode(CommonResultCode.NO_DATA_IS_UPDATED.getCode());
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	@ApiOperation("通过id删除")
	public DefaultResponse<UserInfo> delete(@RequestParam(name="id",required=true) String id) {
		DefaultResponse<UserInfo> result = new DefaultResponse<UserInfo>();
		UserInfo userInfo = userInfoService.getById(id);
		if(userInfo==null) {
			result.setResultCode(CommonResultCode.DATA_NOT_EXISTS.getCode());
		}else {
			boolean ok = userInfoService.removeById(id);
			if(!ok) {
				result.setResultCode(CommonResultCode.NO_DATA_IS_UPDATED.getCode());
			}
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	@ApiOperation("批量删除")
	public DefaultResponse<UserInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		DefaultResponse<UserInfo> result = new DefaultResponse<UserInfo>();
		if(ids==null || "".equals(ids.trim())) {
			result.setResultCode(CommonResultCode.ARGUMENT_NOT_BE_NULL.getCode());
		}else {
			this.userInfoService.removeByIds(Arrays.asList(ids.split(",")));
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	@ApiOperation("通过id查询")
	public DefaultResponse<UserInfo> queryById(@RequestParam(name="id",required=true) String id) {
		DefaultResponse<UserInfo> result = new DefaultResponse<UserInfo>();
		UserInfo userInfo = userInfoService.getById(id);
		if(userInfo==null) {
			result.setResultCode(CommonResultCode.DATA_NOT_EXISTS.getCode());
		}else {
			result.setData(userInfo);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  @ApiOperation("导出excel")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<UserInfo> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              UserInfo userInfo = JSON.parseObject(deString, UserInfo.class);
              queryWrapper = QueryGenerator.initQueryWrapper(userInfo, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<UserInfo> pageList = userInfoService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "user_info列表");
      mv.addObject(NormalExcelConstants.CLASS, UserInfo.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("user_info列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  @ApiOperation("通过excel导入数据")
  public DefaultResponse<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      DefaultResponse result = new DefaultResponse();
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<UserInfo> listUserInfos = ExcelImportUtil.importExcel(file.getInputStream(), UserInfo.class, params);
              for (UserInfo userInfoExcel : listUserInfos) {
                  userInfoService.save(userInfoExcel);
              }
              result.setResultMessage("文件导入成功！数据行数：" + listUserInfos.size());
              return result;
          } catch (Exception e) {
              log.error(e.getMessage());
              result.setResultCode(CommonResultCode.UNKNOWN_EXCEPTION.getCode());
              return result;
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      result.setResultCode(CommonResultCode.EXECUTE_FAIL.getCode());
      return result;
  }

}
