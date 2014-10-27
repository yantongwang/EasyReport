package org.easyframework.report.service;

import org.easyframework.report.dao.ReportingTaskDao;
import org.easyframework.report.data.jdbc.BaseService;
import org.easyframework.report.po.ReportingTaskPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报表任务信息表服务类
 */
@Service
public class ReportingTaskService extends BaseService<ReportingTaskDao, ReportingTaskPo> {
	@Autowired
	public ReportingTaskService(ReportingTaskDao dao) {
		super(dao);
	}
}