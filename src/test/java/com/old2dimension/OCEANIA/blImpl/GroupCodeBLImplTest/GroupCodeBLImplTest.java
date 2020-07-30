package com.old2dimension.OCEANIA.blImpl.GroupCodeBLImplTest;

import com.old2dimension.OCEANIA.bl.LabelBL;
import com.old2dimension.OCEANIA.blImpl.GroupCodeBLImpl;
import com.old2dimension.OCEANIA.dao.*;
import com.old2dimension.OCEANIA.po.*;
import com.old2dimension.OCEANIA.vo.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class GroupCodeBLImplTest {
    @Test
    public void addCodeTest1() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        LabelBL labelBL = mock(LabelBL.class);
        groupCodeBL.setCodeRepository(codeRepository);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);
        groupCodeBL.setLabelBL(labelBL);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(1);
        Code code = new Code(1, 5, "testCode", 2, 1, 1, 1);
        Code newCode = new Code(-1, 1, "testCode", 2, 1, 1, 1);
        GroupCode groupCode = new GroupCode(1, -1);
        groupCode.setId(1);
        ArrayList<VertexLabel> vertexLabels = new ArrayList<>();
        VertexLabel vl1 = new VertexLabel(3, -1, 1, "vTitle1", "vContent1");
        VertexLabel vl2 = new VertexLabel(6, -1, 2, "vTitle2", "vContent2");
        vertexLabels.add(vl1);
        vertexLabels.add(vl2);
        ArrayList<EdgeLabel> edgeLabels = new ArrayList<>();
        EdgeLabel el = new EdgeLabel(9, 1, -1, "eTitle", "eContent");
        edgeLabels.add(el);
        ArrayList<DomainLabel> domainLabels = new ArrayList<>();
        DomainLabel dl = new DomainLabel(11, -1, 1, 2, "dTitle", "dContent");
        domainLabels.add(dl);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, -1);

        when(codeRepository.findCodeById(1)).thenReturn(code);
        when(codeRepository.save(any())).thenReturn(newCode);
        when(groupCodeRepository.save(any())).thenReturn(groupCode);
        when(labelBL.getAllVertexLabel(userAndCodeForm)).thenReturn(vertexLabels);
        when(labelBL.getAllEdgeLabel(userAndCodeForm)).thenReturn(edgeLabels);
        when(labelBL.getAllDomainLabel(userAndCodeForm)).thenReturn(domainLabels);
        when(labelBL.saveAllVertexLabel(any())).thenReturn(vertexLabels);
        when(labelBL.saveAllEdgeLabel(any())).thenReturn(edgeLabels);
        when(labelBL.saveAllDomainLabel(any())).thenReturn(domainLabels);

        ResponseVO responseVO = groupCodeBL.addCode(groupIdAndCodeIdForm);
        Assert.assertEquals(responseVO.getContent(), "Adding group code succeed.");

        deleteFile(new File("src/main/resources/analyzeCode/src/-1"));
    }

    @Test
    public void addCodeTest2() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        LabelBL labelBL = mock(LabelBL.class);
        groupCodeBL.setCodeRepository(codeRepository);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);
        groupCodeBL.setLabelBL(labelBL);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(1);
        Code code = new Code(1, 5, "testCode", 2, 1, 1, 1);
        Code newCode = new Code(-1, 1, "testCode", 2, 1, 1, 1);
        GroupCode groupCode = new GroupCode(1, -1);
        groupCode.setId(1);
        ArrayList<VertexLabel> vertexLabels = new ArrayList<>();
        VertexLabel vl1 = new VertexLabel(3, -1, 1, "vTitle1", "vContent1");
        VertexLabel vl2 = new VertexLabel(6, -1, 2, "vTitle2", "vContent2");
        vertexLabels.add(vl1);
        vertexLabels.add(vl2);
        ArrayList<EdgeLabel> edgeLabels = new ArrayList<>();
        EdgeLabel el = new EdgeLabel(9, 1, -1, "eTitle", "eContent");
        edgeLabels.add(el);
        ArrayList<DomainLabel> domainLabels = new ArrayList<>();
        DomainLabel dl = new DomainLabel(11, -1, 1, 2, "dTitle", "dContent");
        domainLabels.add(dl);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, -1);

        when(codeRepository.findCodeById(1)).thenReturn(code);
        when(codeRepository.save(any())).thenReturn(newCode);
        when(groupCodeRepository.save(any())).thenReturn(groupCode);
        when(labelBL.getAllVertexLabel(userAndCodeForm)).thenReturn(vertexLabels);
        when(labelBL.getAllEdgeLabel(userAndCodeForm)).thenReturn(edgeLabels);
        when(labelBL.getAllDomainLabel(userAndCodeForm)).thenReturn(domainLabels);
        when(labelBL.saveAllVertexLabel(any())).thenReturn(null);

        ResponseVO responseVO = groupCodeBL.addCode(groupIdAndCodeIdForm);
        Assert.assertEquals(responseVO.getMessage(), "Adding code failed(add vertex labels)");
    }

    @Test
    public void addCodeTest3() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        LabelBL labelBL = mock(LabelBL.class);
        groupCodeBL.setCodeRepository(codeRepository);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);
        groupCodeBL.setLabelBL(labelBL);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(1);
        Code code = new Code(1, 5, "testCode", 2, 1, 1, 1);
        Code newCode = new Code(-1, 1, "testCode", 2, 1, 1, 1);
        GroupCode groupCode = new GroupCode(1, -1);
        groupCode.setId(1);
        ArrayList<VertexLabel> vertexLabels = new ArrayList<>();
        VertexLabel vl1 = new VertexLabel(3, -1, 1, "vTitle1", "vContent1");
        VertexLabel vl2 = new VertexLabel(6, -1, 2, "vTitle2", "vContent2");
        vertexLabels.add(vl1);
        vertexLabels.add(vl2);
        ArrayList<EdgeLabel> edgeLabels = new ArrayList<>();
        EdgeLabel el = new EdgeLabel(9, 1, -1, "eTitle", "eContent");
        edgeLabels.add(el);
        ArrayList<DomainLabel> domainLabels = new ArrayList<>();
        DomainLabel dl = new DomainLabel(11, -1, 1, 2, "dTitle", "dContent");
        domainLabels.add(dl);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, -1);

        when(codeRepository.findCodeById(1)).thenReturn(code);
        when(codeRepository.save(any())).thenReturn(newCode);
        when(groupCodeRepository.save(any())).thenReturn(groupCode);
        when(labelBL.getAllVertexLabel(userAndCodeForm)).thenReturn(vertexLabels);
        when(labelBL.getAllEdgeLabel(userAndCodeForm)).thenReturn(edgeLabels);
        when(labelBL.getAllDomainLabel(userAndCodeForm)).thenReturn(domainLabels);
        when(labelBL.saveAllVertexLabel(any())).thenReturn(vertexLabels);
        when(labelBL.saveAllEdgeLabel(any())).thenReturn(null);

        ResponseVO responseVO = groupCodeBL.addCode(groupIdAndCodeIdForm);
        Assert.assertEquals(responseVO.getMessage(), "Adding code failed(add edge labels)");
    }

    @Test
    public void addCodeTest4() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        LabelBL labelBL = mock(LabelBL.class);
        groupCodeBL.setCodeRepository(codeRepository);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);
        groupCodeBL.setLabelBL(labelBL);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(1);
        Code code = new Code(1, 5, "testCode", 2, 1, 1, 1);
        Code newCode = new Code(-1, 1, "testCode", 2, 1, 1, 1);
        GroupCode groupCode = new GroupCode(1, -1);
        groupCode.setId(1);
        ArrayList<VertexLabel> vertexLabels = new ArrayList<>();
        VertexLabel vl1 = new VertexLabel(3, -1, 1, "vTitle1", "vContent1");
        VertexLabel vl2 = new VertexLabel(6, -1, 2, "vTitle2", "vContent2");
        vertexLabels.add(vl1);
        vertexLabels.add(vl2);
        ArrayList<EdgeLabel> edgeLabels = new ArrayList<>();
        EdgeLabel el = new EdgeLabel(9, 1, -1, "eTitle", "eContent");
        edgeLabels.add(el);
        ArrayList<DomainLabel> domainLabels = new ArrayList<>();
        DomainLabel dl = new DomainLabel(11, -1, 1, 2, "dTitle", "dContent");
        domainLabels.add(dl);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, -1);

        when(codeRepository.findCodeById(1)).thenReturn(code);
        when(codeRepository.save(any())).thenReturn(newCode);
        when(groupCodeRepository.save(any())).thenReturn(groupCode);
        when(labelBL.getAllVertexLabel(userAndCodeForm)).thenReturn(vertexLabels);
        when(labelBL.getAllEdgeLabel(userAndCodeForm)).thenReturn(edgeLabels);
        when(labelBL.getAllDomainLabel(userAndCodeForm)).thenReturn(domainLabels);
        when(labelBL.saveAllVertexLabel(any())).thenReturn(vertexLabels);
        when(labelBL.saveAllEdgeLabel(any())).thenReturn(edgeLabels);
        when(labelBL.saveAllDomainLabel(any())).thenReturn(null);

        ResponseVO responseVO = groupCodeBL.addCode(groupIdAndCodeIdForm);
        Assert.assertEquals(responseVO.getMessage(), "Adding code failed(add domain labels)");
    }

    @Test
    public void addCodeTest5() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        LabelBL labelBL = mock(LabelBL.class);
        groupCodeBL.setCodeRepository(codeRepository);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);
        groupCodeBL.setLabelBL(labelBL);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(1);
        Code code = new Code(1, 5, "testCode", 2, 1, 1, 0);
        Code newCode = new Code(-1, 1, "testCode", 2, 1, 1, 0);
        GroupCode groupCode = new GroupCode(1, -1);
        groupCode.setId(1);
        ArrayList<VertexLabel> vertexLabels = new ArrayList<>();
        VertexLabel vl1 = new VertexLabel(3, -1, 1, "vTitle1", "vContent1");
        VertexLabel vl2 = new VertexLabel(6, -1, 2, "vTitle2", "vContent2");
        vertexLabels.add(vl1);
        vertexLabels.add(vl2);
        ArrayList<EdgeLabel> edgeLabels = new ArrayList<>();
        EdgeLabel el = new EdgeLabel(9, 1, -1, "eTitle", "eContent");
        edgeLabels.add(el);
        ArrayList<DomainLabel> domainLabels = new ArrayList<>();
        DomainLabel dl = new DomainLabel(11, -1, 1, 2, "dTitle", "dContent");
        domainLabels.add(dl);
        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, -1);

        when(codeRepository.findCodeById(1)).thenReturn(code);
        when(codeRepository.save(any())).thenReturn(newCode);
        when(groupCodeRepository.save(any())).thenReturn(groupCode);
        when(labelBL.getAllVertexLabel(userAndCodeForm)).thenReturn(vertexLabels);
        when(labelBL.getAllEdgeLabel(userAndCodeForm)).thenReturn(edgeLabels);
        when(labelBL.getAllDomainLabel(userAndCodeForm)).thenReturn(domainLabels);
        when(labelBL.saveAllVertexLabel(any())).thenReturn(vertexLabels);
        when(labelBL.saveAllEdgeLabel(any())).thenReturn(edgeLabels);
        when(labelBL.saveAllDomainLabel(any())).thenReturn(domainLabels);

        ResponseVO responseVO = groupCodeBL.addCode(groupIdAndCodeIdForm);
        Assert.assertEquals(responseVO.getMessage(), "Adding code failed(copy files)");
    }

    @Test
    public void getGroupCodeListTest1() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        WorkPlaceRepository workPlaceRepository = mock(WorkPlaceRepository.class);
        groupCodeBL.setCodeRepository(codeRepository);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);
        groupCodeBL.setWorkPlaceRepository(workPlaceRepository);

        ArrayList<GroupCode> groupCodes = new ArrayList<>();

        GroupCode groupCode = new GroupCode(1,1);
        GroupCode groupCode2 = new GroupCode(1,2);
        groupCodes.add(groupCode);
        groupCodes.add(groupCode2);

        Code code1 = new Code(1, "testCode1", 100, 10, 1, 1);
        Code code2 = new Code(1, "testCode2", 150, 12, 5, 0);
        code1.setId(1);
        code2.setId(2);
        WorkSpace ws = new WorkSpace();
        ws.setDate(new Date());
        when(groupCodeRepository.findAllByGroupId(1)).thenReturn(groupCodes);
        when(codeRepository.findCodeById(1)).thenReturn(code1);
        when(codeRepository.findCodeById(2)).thenReturn(code2);
        when(workPlaceRepository.findLatestWorkSpace(1,1)).thenReturn(ws);
        when(workPlaceRepository.findLatestWorkSpace(1,2)).thenReturn(ws);
        ResponseVO responseVO = groupCodeBL.getGroupCodeList(1);
        Assert.assertEquals(((ArrayList<CodeAndDateForm>) responseVO.getContent()).size(), 2);
        Assert.assertEquals(((ArrayList<CodeAndDateForm>) responseVO.getContent()).get(0).getCodeName(), "testCode1");
    }

    @Test
    public void getGroupCodeListTest2() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);

        when(groupCodeRepository.findAllByGroupId(1)).thenReturn(null);

        ResponseVO responseVO = groupCodeBL.getGroupCodeList(1);
        Assert.assertEquals(responseVO.getMessage(), "Getting group code list failed.");
    }

    @Test
    public void getGroupCodeListTest3() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        groupCodeBL.setCodeRepository(codeRepository);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);
        WorkPlaceRepository workPlaceRepository = mock(WorkPlaceRepository.class);
        groupCodeBL.setWorkPlaceRepository(workPlaceRepository);

        WorkSpace ws = new WorkSpace();
        ws.setDate(new Date());
        ArrayList<GroupCode> groupCodes = new ArrayList<>();
        GroupCode groupCode1 = new GroupCode(1, 1);
        GroupCode groupCode2 = new GroupCode(1, 2);
        groupCodes.add(groupCode1);
        groupCodes.add(groupCode2);
        Code code1 = new Code(1, "testCode1", 100, 10, 1, 1);
        code1.setId(1);

        when(groupCodeRepository.findAllByGroupId(1)).thenReturn(groupCodes);
        when(codeRepository.findCodeById(1)).thenReturn(code1);
        when(codeRepository.findCodeById(2)).thenReturn(null);

        when(workPlaceRepository.findLatestWorkSpace(1,1)).thenReturn(ws);
        when(workPlaceRepository.findLatestWorkSpace(1,2)).thenReturn(ws);

        ResponseVO responseVO = groupCodeBL.getGroupCodeList(1);
        Assert.assertEquals(responseVO.getMessage(), "Getting group code info failed.");
    }

    @Test
    public void getCodeStatisticsTest1() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        LabelBL labelBL = mock(LabelBL.class);
        groupCodeBL.setUserRepository(userRepository);
        groupCodeBL.setGroupMemberRepository(groupMemberRepository);
        groupCodeBL.setLabelBL(labelBL);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(1);
        ArrayList<GroupMember> groupMembers = new ArrayList<>();
        GroupMember groupMember1 = new GroupMember(1, 1, 1);
        GroupMember groupMember2 = new GroupMember(1, 2, 0);
        groupMembers.add(groupMember1);
        groupMembers.add(groupMember2);
        User user1 = new User(1, "leader", "leaderNeedNoPwd");
        User user2 = new User(2, "test", "testPwd");

        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(groupMembers);
        when(userRepository.findUserById(1)).thenReturn(user1);
        when(userRepository.findUserById(2)).thenReturn(user2);
        when(labelBL.countVertexLabel(1, 1)).thenReturn(100);
        when(labelBL.countEdgeLabel(1, 1)).thenReturn(50);
        when(labelBL.countDomainLabel(1, 1)).thenReturn(2);
        when(labelBL.countVertexLabel(2, 1)).thenReturn(188);
        when(labelBL.countEdgeLabel(2, 1)).thenReturn(77);
        when(labelBL.countDomainLabel(2, 1)).thenReturn(1);

        ResponseVO responseVO = groupCodeBL.getCodeStatistics(groupIdAndCodeIdForm);
        HashMap<String, HashMap> sumMap = (HashMap<String, HashMap>) responseVO.getContent();
        Assert.assertEquals(sumMap.get("vertexLabel").get("1 leader"), 100);
        Assert.assertEquals(sumMap.get("domainLabel").get("2 test"), 1);
    }

    @Test
    public void getCodeStatisticsTest2() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        UserRepository userRepository = mock(UserRepository.class);
        GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
        LabelBL labelBL = mock(LabelBL.class);
        groupCodeBL.setUserRepository(userRepository);
        groupCodeBL.setGroupMemberRepository(groupMemberRepository);
        groupCodeBL.setLabelBL(labelBL);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(1);
        ArrayList<GroupMember> groupMembers = new ArrayList<>();
        GroupMember groupMember1 = new GroupMember(1, 1, 1);
        GroupMember groupMember2 = new GroupMember(1, 2, 0);
        groupMembers.add(groupMember1);
        groupMembers.add(groupMember2);
        User user1 = new User(1, "leader", "leaderNeedNoPwd");

        when(groupMemberRepository.findGroupMembersByGroupId(1)).thenReturn(groupMembers);
        when(userRepository.findUserById(1)).thenReturn(user1);
        when(userRepository.findUserById(2)).thenReturn(null);
        when(labelBL.countVertexLabel(1, 1)).thenReturn(100);
        when(labelBL.countEdgeLabel(1, 1)).thenReturn(50);
        when(labelBL.countDomainLabel(1, 1)).thenReturn(2);

        ResponseVO responseVO = groupCodeBL.getCodeStatistics(groupIdAndCodeIdForm);
        Assert.assertEquals(responseVO.getMessage(), "Getting user info failed.");
    }

    @Test
    public void deleteCodeTest1() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        CodeRepository codeRepository = mock(CodeRepository.class);
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        groupCodeBL.setCodeRepository(codeRepository);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(-1);
        GroupCode groupCode = new GroupCode(1, -1);

        when(groupCodeRepository.findGroupCodeByCodeIdAndGroupId(-1, 1)).thenReturn(groupCode);
        doNothing().when(codeRepository).deleteById(-1);

        ResponseVO responseVO = groupCodeBL.deleteCode(groupIdAndCodeIdForm);
        Assert.assertEquals(responseVO.getContent(), "Deleting code succeed.");
    }

    @Test
    public void deleteCodeTest2() {
        GroupCodeBLImpl groupCodeBL = new GroupCodeBLImpl();
        GroupCodeRepository groupCodeRepository = mock(GroupCodeRepository.class);
        groupCodeBL.setGroupCodeRepository(groupCodeRepository);

        GroupIdAndCodeIdForm groupIdAndCodeIdForm = new GroupIdAndCodeIdForm();
        groupIdAndCodeIdForm.setGroupId(1);
        groupIdAndCodeIdForm.setCodeId(-1);

        when(groupCodeRepository.findGroupCodeByCodeIdAndGroupId(-1, 1)).thenReturn(null);

        ResponseVO responseVO = groupCodeBL.deleteCode(groupIdAndCodeIdForm);
        Assert.assertEquals(responseVO.getMessage(), "Don't have the access of deleting");
    }

    private boolean deleteFile(File file) {
        boolean res = true;
        if (file.isFile()) {
            boolean isSuccess = file.delete();
            if (!isSuccess) {
                System.out.println("删除文件失败");
                return false;
            }
            return true;
        }
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("list file fail");
            return false;
        }
        for (File cur : files) {
            if (cur.isDirectory()) {
                res = res & deleteFile(cur);
            }
            else{
                res = res& deleteFile(cur);
            }
        }
        res = res & file.delete();
        return res;
    }
}
