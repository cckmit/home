package com.neusoft.mid.cloong.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

import org.junit.Test;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.vm.core.VMOperatorType;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMOperatorErrorInfo;

public class VMStatusServiceTest {

    private VMJudge judge = new VMJudge();

    VMOperatorErrorInfo erroInfo = new VMOperatorErrorInfo();

    private Holder<VMOperatorErrorInfo> holder = new Holder<VMOperatorErrorInfo>(erroInfo);

    @Test
    public void testAddVMStatus() throws SQLException {
        VMStatusService vmStatusService = new VMStatusService();
        IbatisDAO mockDao = prepareMockDao();
        vmStatusService.setIbatisDAO(mockDao);
        vmStatusService.setJudge(judge);
        vmStatusService.init();
        vmStatusService.addVMStatus("vm5", VMStatus.PAUSE);
        assertTrue(vmStatusService.judgeVMOperator("vm5", VMOperatorType.VM_RESUME, holder));
        vmStatusService.addVMStatus("vm1", VMStatus.PAUSE);
        assertTrue(vmStatusService.judgeVMOperator("vm1", VMOperatorType.VM_RESUME, holder));
    }

    @Test
    public void testRemoveVMStatus() throws SQLException {
        VMStatusService vmStatusService = new VMStatusService();
        IbatisDAO mockDao = prepareMockDao();
        vmStatusService.setIbatisDAO(mockDao);
        vmStatusService.setJudge(judge);
        vmStatusService.init();
        vmStatusService.removeVMStatus("vm1");
        assertFalse(vmStatusService.judgeVMDelete("vm1", holder));
    }

    @Test
    public void testJudgeVMOperator() throws SQLException {
        VMStatusService vmStatusService = new VMStatusService();
        IbatisDAO mockDao = prepareMockDao();
        vmStatusService.setIbatisDAO(mockDao);
        vmStatusService.setJudge(judge);
        vmStatusService.init();
        assertTrue(vmStatusService.judgeVMOperator("vm1", VMOperatorType.VM_STOP, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm1", VMOperatorType.VM_START, holder));
        assertTrue(vmStatusService.judgeVMOperator("vm1", VMOperatorType.VM_REBOOT, holder));
        assertTrue(vmStatusService.judgeVMOperator("vm1", VMOperatorType.VM_PAUSE, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm1", VMOperatorType.VM_RESUME, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm2", VMOperatorType.VM_STOP, holder));
        assertTrue(vmStatusService.judgeVMOperator("vm2", VMOperatorType.VM_START, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm2", VMOperatorType.VM_REBOOT, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm2", VMOperatorType.VM_PAUSE, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm2", VMOperatorType.VM_RESUME, holder));
        assertTrue(vmStatusService.judgeVMOperator("vm3", VMOperatorType.VM_STOP, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm3", VMOperatorType.VM_START, holder));
        assertTrue(vmStatusService.judgeVMOperator("vm3", VMOperatorType.VM_REBOOT, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm3", VMOperatorType.VM_PAUSE, holder));
        assertTrue(vmStatusService.judgeVMOperator("vm3", VMOperatorType.VM_RESUME, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm4", VMOperatorType.VM_STOP, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm4", VMOperatorType.VM_START, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm4", VMOperatorType.VM_REBOOT, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm4", VMOperatorType.VM_PAUSE, holder));
        assertFalse(vmStatusService.judgeVMOperator("vm4", VMOperatorType.VM_RESUME, holder));

    }

    private IbatisDAO prepareMockDao() throws SQLException {
        IbatisDAO mockDao = mock(IbatisDAO.class);
        List<VMInstanceInfo> vmInfos = new ArrayList<VMInstanceInfo>();
        VMInstanceInfo vm1 = new VMInstanceInfo();
        vm1.setVmId("vm1");
        vm1.setStatus(VMStatus.RUNNING);
        VMInstanceInfo vm2 = new VMInstanceInfo();
        vm2.setVmId("vm2");
        vm2.setStatus(VMStatus.STOP);
        VMInstanceInfo vm3 = new VMInstanceInfo();
        vm3.setVmId("vm3");
        vm3.setStatus(VMStatus.PAUSE);
        VMInstanceInfo vm4 = new VMInstanceInfo();
        vm4.setVmId("vm4");
        vm4.setStatus(VMStatus.CREATING);
        vmInfos.add(vm1);
        vmInfos.add(vm2);
        vmInfos.add(vm3);
        vmInfos.add(vm4);
        when(mockDao.getData(eq("queryAllStatus"), anyObject())).thenReturn(vmInfos);
        return mockDao;
    }

    @Test
    public void testJudgeVMDelete() throws SQLException {
        VMStatusService vmStatusService = new VMStatusService();
        IbatisDAO mockDao = prepareMockDao();
        vmStatusService.setIbatisDAO(mockDao);
        vmStatusService.setJudge(judge);
        vmStatusService.init();
        assertFalse(vmStatusService.judgeVMDelete("vm5", holder));
        assertTrue(vmStatusService.judgeVMDelete("vm1", holder));
        assertTrue(vmStatusService.judgeVMDelete("vm2", holder));
        assertTrue(vmStatusService.judgeVMDelete("vm3", holder));
        assertFalse(vmStatusService.judgeVMDelete("vm4", holder));
    }

}
