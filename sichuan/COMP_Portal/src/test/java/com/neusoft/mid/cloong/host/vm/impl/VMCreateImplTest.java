package com.neusoft.mid.cloong.host.vm.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;

import com.neusoft.mid.cloong.host.vm.core.VMCreateReq;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;

public class VMCreateImplTest {

    @Test
    public void testCreateVMSuccess()  throws IOException, InvalidProtocolException , Exception{
        VMCreateImpl vmCreateImpl = new VMCreateImpl();

        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "00000000");
        HttpSyncRespData resp = new HttpSyncRespData("", "", rc);
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenReturn(resp);
        vmCreateImpl.setHttpTimeOut(50);
        vmCreateImpl.setReceiveTimeout(5000);
        vmCreateImpl.setSenderEntry(mockSend);
        vmCreateImpl.setUrl("");
        VMCreateReq req = new VMCreateReq();
        req.setStandardId("stand001");
        req.setOsId("os001");
        req.setNum("1");
        req.setSubNetwork("10.10.10.10");
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        req.setPassword("password");
        req.setResourceUrl("resourceUrl");
        req.setTransactionID("transactionID");
        req.setTimestamp("20130203121212");
        
        assertEquals("00000000", vmCreateImpl.createVM(req).getResultCode());
    }

    @Test
    public void testCreateVMInvalidProtocolException()  throws IOException, InvalidProtocolException , Exception{
        VMCreateImpl vmCreateImpl = new VMCreateImpl();

        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "00000000");
        HttpSyncRespData resp = new HttpSyncRespData("", "", rc);
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenThrow(new InvalidProtocolException(null, null));
        vmCreateImpl.setHttpTimeOut(50);
        vmCreateImpl.setReceiveTimeout(5000);
        vmCreateImpl.setSenderEntry(mockSend);
        vmCreateImpl.setUrl("");
        VMCreateReq req = new VMCreateReq();
        req.setStandardId("stand001");
        req.setOsId("os001");
        req.setNum("1");
        req.setSubNetwork("10.10.10.10");
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        req.setPassword("password");
        req.setResourceUrl("resourceUrl");
        req.setTransactionID("transactionID");
        req.setTimestamp("20130203121212");
        
        assertEquals("10001", vmCreateImpl.createVM(req).getResultCode());
    }
    @Test
    public void testCreateVMInvalidIOException()  throws IOException, InvalidProtocolException , Exception{
        VMCreateImpl vmCreateImpl = new VMCreateImpl();

        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "00000000");
        HttpSyncRespData resp = new HttpSyncRespData("", "", rc);
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenThrow(new IOException(null, null));
        vmCreateImpl.setHttpTimeOut(50);
        vmCreateImpl.setReceiveTimeout(5000);
        vmCreateImpl.setSenderEntry(mockSend);
        vmCreateImpl.setUrl("");
        VMCreateReq req = new VMCreateReq();
        req.setStandardId("stand001");
        req.setOsId("os001");
        req.setNum("1");
        req.setSubNetwork("10.10.10.10");
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        req.setPassword("password");
        req.setResourceUrl("resourceUrl");
        req.setTransactionID("transactionID");
        req.setTimestamp("20130203121212");
        
        assertEquals("10002", vmCreateImpl.createVM(req).getResultCode());
    }

    @Test
    public void testCreateVMOtherException()  throws IOException, InvalidProtocolException , Exception{
        VMCreateImpl vmCreateImpl = new VMCreateImpl();

        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "00000000");
        HttpSyncRespData resp = new HttpSyncRespData("", "", rc);
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenThrow(new RuntimeException(null, null));
        vmCreateImpl.setHttpTimeOut(50);
        vmCreateImpl.setReceiveTimeout(5000);
        vmCreateImpl.setSenderEntry(mockSend);
        vmCreateImpl.setUrl("");
        VMCreateReq req = new VMCreateReq();
        req.setStandardId("stand001");
        req.setOsId("os001");
        req.setNum("1");
        req.setSubNetwork("10.10.10.10");
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        req.setPassword("password");
        req.setResourceUrl("resourceUrl");
        req.setTransactionID("transactionID");
        req.setTimestamp("20130203121212");
        
        assertEquals("10003", vmCreateImpl.createVM(req).getResultCode());
    }

    @Test
    public void testCreateVMFail()  throws IOException, InvalidProtocolException , Exception{
        VMCreateImpl vmCreateImpl = new VMCreateImpl();

        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "60000004");
        HttpSyncRespData resp = new HttpSyncRespData("", "", rc);
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenReturn(resp);
        vmCreateImpl.setHttpTimeOut(50);
        vmCreateImpl.setReceiveTimeout(5000);
        vmCreateImpl.setSenderEntry(mockSend);
        vmCreateImpl.setUrl("");
        VMCreateReq req = new VMCreateReq();
        req.setStandardId("stand001");
        req.setOsId("os001");
        req.setNum("1");
        req.setSubNetwork("10.10.10.10");
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        req.setPassword("password");
        req.setResourceUrl("resourceUrl");
        req.setTransactionID("transactionID");
        req.setTimestamp("20130203121212");
        
        assertEquals("60000004", vmCreateImpl.createVM(req).getResultCode());
    }
}
