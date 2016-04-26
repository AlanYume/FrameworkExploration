package org.smart4j.chapter5.controller;

import org.smart4j.chapter5.model.Customer;
import org.smart4j.chapter5.service.CustomerService;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;

import java.util.List;
import java.util.Map;

/**
 * 处理客户管理相关请求
 */
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    /**
     * 进入 客户列表 界面
     */
    @Action("get:/customer")
    public View index() {
        final List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }

    /**
     * 显示客户基本信息
     */
    @Action("get:/customer_show")
    public View show(final Param param) {
        final long id = param.getLong("id");
        final Customer customer = customerService.getCustomer(id);
        return new View("customer_show.jsp").addModel("customer", customer);
    }

    /**
     * 进入 创建客户 界面
     */
    @Action("get:/customer_create")
    public View create() {
        return new View("customer_create.jsp");
    }

    /**
     * 处理 创建客户 请求
     */
    @Action("post:/customer_create")
    public Data createSubmit(final Param param) {
        final Map<String, Object> fieldMap = param.getFieldMap();
        final FileParam fileParam = param.getFile("photo");
        final boolean result = customerService.createCustomer(fieldMap, fileParam);
        return new Data(result);
    }

    /**
     * 进入 编辑客户 界面
     */
    @Action("get:/customer_edit")
    public View edit(final Param param) {
        final long id = param.getLong("id");
        final Customer customer = customerService.getCustomer(id);
        return new View("customer_edit.jsp").addModel("customer", customer);
    }

    /**
     * 处理 编辑客户 请求
     */
    @Action("put:/customer_edit")
    public Data editSubmit(final Param param) {
        final long id = param.getLong("id");
        final Map<String, Object> fieldMap = param.getFieldMap();
        final boolean result = customerService.updateCustomer(id, fieldMap);
        return new Data(result);
    }

    /**
     * 处理 删除客户 请求
     */
    @Action("delete:/customer_edit")
    public Data delete(final Param param) {
        final long id = param.getLong("id");
        final boolean result = customerService.deleteCustomer(id);
        return new Data(result);
    }
}