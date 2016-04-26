package org.smart4j.chapter5.soap;

import org.junit.Assert;
import org.junit.Test;
import org.smart4j.chapter5.model.Customer;
import org.smart4j.plugin.soap.SoapHelper;

/**
 * 客户 SOAP 服务单元测试
 */
public class CustomerSoapServiceTest {

    @Test
    public void getCustomerTest() {
        final String wsdl = "http://localhost:8080/soap/CustomerSoapService";
        final CustomerSoapService customerSoapService = SoapHelper.createClient(wsdl,
                CustomerSoapService.class);
        final Customer customer = customerSoapService.getCustomer(1);
        Assert.assertNotNull(customer);
    }
}
