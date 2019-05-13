package org.liujk.custom.code.generator.modules.order.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.liujk.custom.code.generator.common.api.Result;
import org.liujk.custom.code.generator.common.api.CommonResultCode;
import org.liujk.custom.code.generator.common.api.DefaultResponse;
import org.liujk.custom.code.generator.modules.system.query.QueryGenerator;
import org.liujk.custom.code.generator.common.util.oConvertUtils;
import org.liujk.custom.code.generator.modules.order.entity.OrderCustom;
import org.liujk.custom.code.generator.modules.order.entity.OrderTicket;
import org.liujk.custom.code.generator.modules.order.entity.OrderMain;
import org.liujk.custom.code.generator.modules.order.vo.OrderMainPage;
import org.liujk.custom.code.generator.modules.order.service.IOrderMainService;
import org.liujk.custom.code.generator.modules.order.service.IOrderCustomService;
import org.liujk.custom.code.generator.modules.order.service.IOrderTicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;

 /**
 * @Title: Controller
 * @Description: 订单
 * @author： jeecg-boot
 * @date：   2019-05-13
 * @version： V1.0
 */
@RestController
@RequestMapping("/order/orderMain")
@Slf4j
public class OrderMainController {
	@Autowired
	private IOrderMainService orderMainService;
	@Autowired
	private IOrderCustomService orderCustomService;
	@Autowired
	private IOrderTicketService orderTicketService;
	
	/**
	  * 分页列表查询
	 * @param orderMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	@ApiOperation("分页列表查询")
	public DefaultResponse<IPage<OrderMain>> queryPageList(OrderMain orderMain,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		DefaultResponse<IPage<OrderMain>> result = new DefaultResponse<IPage<OrderMain>>();
		QueryWrapper<OrderMain> queryWrapper = QueryGenerator.initQueryWrapper(orderMain, req.getParameterMap());
		Page<OrderMain> page = new Page<OrderMain>(pageNo, pageSize);
		IPage<OrderMain> pageList = orderMainService.page(page, queryWrapper);
		result.setData(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param orderMainPage
	 * @return
	 */
	@PostMapping(value = "/add")
	@ApiOperation("添加")
	public DefaultResponse<OrderMain> add(@RequestBody OrderMainPage orderMainPage) {
		DefaultResponse<OrderMain> result = new DefaultResponse<OrderMain>();
		try {
			OrderMain orderMain = new OrderMain();
			BeanUtils.copyProperties(orderMainPage, orderMain);
			
			orderMainService.saveMain(orderMain, orderMainPage.getOrderCustomList(),orderMainPage.getOrderTicketList());
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result.setResultCode(CommonResultCode.UNKNOWN_EXCEPTION.getCode());
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param orderMainPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	@ApiOperation("编辑")
	public DefaultResponse<OrderMain> edit(@RequestBody OrderMainPage orderMainPage) {
		DefaultResponse<OrderMain> result = new DefaultResponse<OrderMain>();
		OrderMain orderMain = new OrderMain();
		BeanUtils.copyProperties(orderMainPage, orderMain);
		OrderMain orderMainEntity = orderMainService.getById(orderMain.getId());
		if(orderMainEntity==null) {
			result.setResultCode(CommonResultCode.DATA_NOT_EXISTS.getCode());
		}else {
			boolean ok = orderMainService.updateById(orderMain);
			orderMainService.updateMain(orderMain, orderMainPage.getOrderCustomList(),orderMainPage.getOrderTicketList());
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
	public DefaultResponse<OrderMain> delete(@RequestParam(name="id",required=true) String id) {
		DefaultResponse<OrderMain> result = new DefaultResponse<OrderMain>();
		OrderMain orderMain = orderMainService.getById(id);
		if(orderMain==null) {
			result.setResultCode(CommonResultCode.DATA_NOT_EXISTS.getCode());
		}else {
			orderMainService.delMain(id);
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
	public DefaultResponse<OrderMain> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		DefaultResponse<OrderMain> result = new DefaultResponse<OrderMain>();
		if(ids==null || "".equals(ids.trim())) {
			result.setResultCode(CommonResultCode.ARGUMENT_NOT_BE_NULL.getCode());
		}else {
			this.orderMainService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public DefaultResponse<OrderMain> queryById(@RequestParam(name="id",required=true) String id) {
		DefaultResponse<OrderMain> result = new DefaultResponse<OrderMain>();
		OrderMain orderMain = orderMainService.getById(id);
		if(orderMain==null) {
			result.setResultCode(CommonResultCode.DATA_NOT_EXISTS.getCode());
		}else {
			result.setData(orderMain);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryOrderCustomByMainId")
	@ApiOperation("通过id查询")
	public DefaultResponse<List<OrderCustom>> queryOrderCustomListByMainId(@RequestParam(name="id",required=true) String id) {
		DefaultResponse<List<OrderCustom>> result = new DefaultResponse<List<OrderCustom>>();
		List<OrderCustom> orderCustomList = orderCustomService.selectByMainId(id);
		result.setData(orderCustomList);
		return result;
	}
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryOrderTicketByMainId")
	@ApiOperation("通过id查询")
	public DefaultResponse<List<OrderTicket>> queryOrderTicketListByMainId(@RequestParam(name="id",required=true) String id) {
		DefaultResponse<List<OrderTicket>> result = new DefaultResponse<List<OrderTicket>>();
		List<OrderTicket> orderTicketList = orderTicketService.selectByMainId(id);
		result.setData(orderTicketList);
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
      QueryWrapper<OrderMain> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              OrderMain orderMain = JSON.parseObject(deString, OrderMain.class);
              queryWrapper = QueryGenerator.initQueryWrapper(orderMain, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<OrderMainPage> pageList = new ArrayList<OrderMainPage>();
      List<OrderMain> orderMainList = orderMainService.list(queryWrapper);
      for (OrderMain orderMain : orderMainList) {
          OrderMainPage vo = new OrderMainPage();
          BeanUtils.copyProperties(orderMain, vo);
          List<OrderCustom> orderCustomList = orderCustomService.selectByMainId(orderMain.getId());
          vo.setOrderCustomList(orderCustomList);
          List<OrderTicket> orderTicketList = orderTicketService.selectByMainId(orderMain.getId());
          vo.setOrderTicketList(orderTicketList);
          pageList.add(vo);
      }
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "订单列表");
      mv.addObject(NormalExcelConstants.CLASS, OrderMainPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("订单列表数据", "导出人:Jeecg", "导出信息"));
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
              List<OrderMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), OrderMainPage.class, params);
              for (OrderMainPage page : list) {
                  OrderMain po = new OrderMain();
                  BeanUtils.copyProperties(page, po);
                  orderMainService.saveMain(po, page.getOrderCustomList(),page.getOrderTicketList());
              }
              result.setResultMessage("文件导入成功！数据行数：" + list.size());
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
