package com.neusoft.mid.cloong.host.vm.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;

import com.neusoft.mid.cloong.host.vm.core.VMOperatorType;
import com.neusoft.mid.cloong.host.vm.core.VmOperatorReq;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;

public class VMManagerImplTest {

    @Test
    public void testOperateVmSuccess() throws IOException, InvalidProtocolException {
        VMManagerImpl vmManager = new VMManagerImpl();
        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "00000000");
        HttpSyncRespData resp = new HttpSyncRespData("", "", rc);
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenReturn(resp);
        vmManager.setHttpTimeOut(50);
        vmManager.setReceiveTimeout(5000);
        vmManager.setSenderEntry(mockSend);
        vmManager.setUrl("");
        VmOperatorReq req = new VmOperatorReq();
        req.setVmId("vm001");
        req.setType(VMOperatorType.VM_PAUSE);
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        assertEquals("00000000", vmManager.operateVm(req).getResultCode());
    }

    @Test
    public void testOperateVmInvalidProtocolException() throws IOException,
            InvalidProtocolException {
        VMManagerImpl vmManager = new VMManagerImpl();
        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "00000000");
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenThrow(new InvalidProtocolException(null, null));
        vmManager.setHttpTimeOut(50);
        vmManager.setReceiveTimeout(5000);
        vmManager.setSenderEntry(mockSend);
        vmManager.setUrl("");
        VmOperatorReq req = new VmOperatorReq();
        req.setVmId("vm001");
        req.setType(VMOperatorType.VM_PAUSE);
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        assertEquals("10001", vmManager.operateVm(req).getResultCode());
    }

    @Test
    public void testOperateVmIOException() throws IOException, InvalidProtocolException {
        VMManagerImpl vmManager = new VMManagerImpl();
        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "00000000");
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenThrow(new IOException());
        vmManager.setHttpTimeOut(50);
        vmManager.setReceiveTimeout(5000);
        vmManager.setSenderEntry(mockSend);
        vmManager.setUrl("");
        VmOperatorReq req = new VmOperatorReq();
        req.setVmId("vm001");
        req.setType(VMOperatorType.VM_PAUSE);
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        assertEquals("10002", vmManager.operateVm(req).getResultCode());
    }

    @Test
    public void testOperateVmOtherException() throws IOException, InvalidProtocolException {
        VMManagerImpl vmManager = new VMManagerImpl();
        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "00000000");
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenThrow(new RuntimeException());
        vmManager.setHttpTimeOut(50);
        vmManager.setReceiveTimeout(5000);
        vmManager.setSenderEntry(mockSend);
        vmManager.setUrl("");
        VmOperatorReq req = new VmOperatorReq();
        req.setVmId("vm001");
        req.setType(VMOperatorType.VM_PAUSE);
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        assertEquals("10003", vmManager.operateVm(req).getResultCode());
    }

    @Test
    public void testOperateVmFail() throws IOException, InvalidProtocolException {
        VMManagerImpl vmManager = new VMManagerImpl();
        HttpSyncSendHelper mockSend = mock(HttpSyncSendHelper.class);
        RuntimeContext rc = new RuntimeContext();
        rc.setAttribute("ResultCode", "52000000");
        HttpSyncRespData resp = new HttpSyncRespData("", "", rc);
        when(
                mockSend.sendHttpRequest((RuntimeContext) anyObject(), anyString(), anyInt(),
                        anyInt())).thenReturn(resp);
        vmManager.setHttpTimeOut(50);
        vmManager.setReceiveTimeout(5000);
        vmManager.setSenderEntry(mockSend);
        vmManager.setUrl("");
        VmOperatorReq req = new VmOperatorReq();
        req.setVmId("vm001");
        req.setType(VMOperatorType.VM_PAUSE);
        req.setResourcePoolId("resourcePoolId");
        req.setResourcePoolPartId("resourcePoolPartId");
        assertEquals("52000000", vmManager.operateVm(req).getResultCode());
    }
}
