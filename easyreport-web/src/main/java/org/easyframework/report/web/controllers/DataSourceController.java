package org.easyframework.report.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.easyframework.report.common.viewmodel.JsonResult;
import org.easyframework.report.data.PageInfo;
import org.easyframework.report.po.DataSourcePo;
import org.easyframework.report.service.DataSourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 报表数据源控制器
 */
@Controller
@RequestMapping(value = "report/ds")
public class DataSourceController extends AbstractController {
	@Resource
	private DataSourceService datasourceService;

	@RequestMapping(value = { "", "/", "/index" })
	public String index() {
		return "/ds";
	}

	@RequestMapping(value = "/getall")
	@ResponseBody
	public List<DataSourcePo> getAll(HttpServletRequest request) {
		return this.datasourceService.getAll();
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(Integer page, Integer rows, HttpServletRequest request) {
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 50;

		PageInfo pageInfo = new PageInfo((page - 1) * rows, rows);
		List<DataSourcePo> list = this.datasourceService.getByPage(pageInfo);

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("total", pageInfo.getTotals());
		modelMap.put("rows", list);
		return modelMap;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public JsonResult add(DataSourcePo po, HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "");

		try {
			po.setUid(UUID.randomUUID().toString());
			this.datasourceService.add(po);
			this.setSuccessResult(result, "");
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public JsonResult edit(DataSourcePo po, HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "");

		try {
			this.datasourceService.edit(po);
			this.setSuccessResult(result, "");
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/testconnection")
	@ResponseBody
	public JsonResult testConnection(String url, String pass, String user) {
		JsonResult result = new JsonResult(false, "");

		try {
			result.setSuccess(this.datasourceService.getDao().testConnection(url, pass, user));
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/remove")
	@ResponseBody
	public JsonResult remove(int id, HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "");

		try {
			this.datasourceService.remove(id);
			this.setSuccessResult(result, "");
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}

	@RequestMapping(value = "/batchremove")
	@ResponseBody
	public JsonResult remove(String ids, HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "您没有权限执行该操作!");

		try {
			this.datasourceService.remove(ids);
			this.setSuccessResult(result, "");
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}

		return result;
	}
}