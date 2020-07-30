package com.old2dimension.OCEANIA.blImpl.LabelBLImplTest;

import com.old2dimension.OCEANIA.blImpl.LabelBLImpl;
import com.old2dimension.OCEANIA.dao.DomainLabelRepository;
import com.old2dimension.OCEANIA.dao.EdgeLabelRepository;
import com.old2dimension.OCEANIA.dao.VertexLabelRepository;
import com.old2dimension.OCEANIA.po.DomainLabel;
import com.old2dimension.OCEANIA.po.EdgeLabel;
import com.old2dimension.OCEANIA.po.VertexLabel;
import com.old2dimension.OCEANIA.vo.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
public class LabelBLImplTest {
    @Test
    public void TestNoteVertex1() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "mytile","rua");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1,"mytile", "rua");
        List<VertexLabel> vertexLabels = new ArrayList<VertexLabel>();
        vertexLabels.add(vertexLabel);
        when(vertexLabelRepository.findVertexLabelsByCodeIdAndUserId(1, 1)).thenReturn(vertexLabels);
        when(vertexLabelRepository.save(vertexLabel)).thenReturn(vertexLabel);

        ResponseVO responseVO = labelBL.noteVertex(vertexLabelVO);
        Assert.assertEquals(((VertexLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestNoteVertex2() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "mytile","rua");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1,"mytile", "rua");
        List<VertexLabel> vertexLabels = new ArrayList<VertexLabel>();
        //vertexLabels.add(vertexLabel);
        when(vertexLabelRepository.findVertexLabelsByCodeIdAndUserId(1, 1)).thenReturn(vertexLabels);
        when(vertexLabelRepository.save(vertexLabel)).thenReturn(vertexLabel);

        ResponseVO responseVO = labelBL.noteVertex(vertexLabelVO);
        Assert.assertEquals(((VertexLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestNoteEdge1() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);
        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "mytile","rua");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1,"mytile", "rua");
        List<EdgeLabel> edgeLabels = new ArrayList<EdgeLabel>();
        edgeLabels.add(edgeLabel);
        when(edgeLabelRepository.findEdgeLabelsByCodeIdAndUserId(1,1)).thenReturn(edgeLabels);
        when(edgeLabelRepository.save(edgeLabel)).thenReturn(edgeLabel);

        ResponseVO responseVO = labelBL.noteEdge(edgeLabelVO);
        Assert.assertEquals(((EdgeLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestNoteEdge2() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);
        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "mytile","rua");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1,"mytile", "rua");
        List<EdgeLabel> edgeLabels = new ArrayList<EdgeLabel>();
        //edgeLabels.add(edgeLabel);
        when(edgeLabelRepository.findEdgeLabelsByCodeIdAndUserId(1,1)).thenReturn(edgeLabels);
        when(edgeLabelRepository.save(edgeLabel)).thenReturn(edgeLabel);

        ResponseVO responseVO = labelBL.noteEdge(edgeLabelVO);
        Assert.assertEquals(((EdgeLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestNoteDomain1() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);

        DomainLabelVO domainLabelVO = new DomainLabelVO(1,1,1, 1,"title","rua");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "title","rua");
        List<DomainLabel> domainLabels = new ArrayList<DomainLabel>();
        domainLabels.add(domainLabel);
        when(domainLabelRepository.findDomainLabelsByCodeIdAndUserId(1, 1)).thenReturn(domainLabels);
        when(domainLabelRepository.save(domainLabel)).thenReturn(domainLabel);

        ResponseVO responseVO = labelBL.noteDomain(domainLabelVO);
        Assert.assertEquals(((DomainLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestNoteDomain2() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);

        DomainLabelVO domainLabelVO = new DomainLabelVO(1,1,1, 1,"title","rua");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "title","rua");
        List<DomainLabel> domainLabels = new ArrayList<DomainLabel>();
        //domainLabels.add(domainLabel);
        when(domainLabelRepository.findDomainLabelsByCodeIdAndUserId(1, 1)).thenReturn(domainLabels);
        when(domainLabelRepository.save(domainLabel)).thenReturn(domainLabel);

        ResponseVO responseVO = labelBL.noteDomain(domainLabelVO);
        Assert.assertEquals(((DomainLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestDeleteVertexLabel1() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "title","rua");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1,"title", "rua");
        vertexLabelVO.setId(1);
        when(vertexLabelRepository.findVertexLabelById(1)).thenReturn(vertexLabel);
        doNothing().when(vertexLabelRepository).deleteVertexLabelById(1);
        ResponseVO responseVO = labelBL.deleteVertexLabel(vertexLabelVO);
        Assert.assertEquals(responseVO.getContent(), "delete label successfully");
    }

    @Test
    public void TestDeleteVertexLabel2() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "title","rua");
        vertexLabelVO.setId(1);
        when(vertexLabelRepository.findVertexLabelById(1)).thenReturn(null);
        doNothing().when(vertexLabelRepository).deleteVertexLabelById(1);
        ResponseVO responseVO = labelBL.deleteVertexLabel(vertexLabelVO);
        Assert.assertEquals(responseVO.getMessage(), "no such label");
    }

    @Test
    public void TestDeleteEdgeLabel1() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);

        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "title","rua");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1,"title", "rua");
        edgeLabelVO.setId(1);
        when(edgeLabelRepository.findEdgeLabelById(1)).thenReturn(edgeLabel);
        doNothing().when(edgeLabelRepository).deleteEdgeLabelById(1);
        ResponseVO responseVO = labelBL.deleteEdgeLabel(edgeLabelVO);
        Assert.assertEquals(responseVO.getContent(), "delete label successfully");
    }

    @Test
    public void TestDeleteEdgeLabel2() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);

        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "title","rua");
        edgeLabelVO.setId(1);
        when(edgeLabelRepository.findEdgeLabelById(1)).thenReturn(null);
        doNothing().when(edgeLabelRepository).deleteEdgeLabelById(1);
        ResponseVO responseVO = labelBL.deleteEdgeLabel(edgeLabelVO);
        Assert.assertEquals(responseVO.getMessage(), "no such label");
    }

    @Test
    public void TestDeleteDomainLabel1() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);

        DomainLabelVO domainLabelVO = new DomainLabelVO(1, 1, 1, 1, "title","rua");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "title","rua");
        domainLabelVO.setId(1);
        when(domainLabelRepository.findDomainLabelById(1)).thenReturn(domainLabel);
        doNothing().when(domainLabelRepository).deleteDomainLabelById(1);
        ResponseVO responseVO = labelBL.deleteDomainLabel(domainLabelVO);
        Assert.assertEquals(responseVO.getContent(), "delete label successfully");
    }

    @Test
    public void TestDeleteDomainLabel2() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);

        DomainLabelVO domainLabelVO = new DomainLabelVO(1, 1, 1, 1, "title","rua");
        domainLabelVO.setId(1);
        when(domainLabelRepository.findDomainLabelById(1)).thenReturn(null);
        doNothing().when(domainLabelRepository).deleteDomainLabelById(1);
        ResponseVO responseVO = labelBL.deleteDomainLabel(domainLabelVO);
        Assert.assertEquals(responseVO.getMessage(), "no such label");
    }

    @Test
    public void TestGetOneVertexLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1,"title", "rua");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "title","rua");
        List<VertexLabel> vertexLabels = new ArrayList<VertexLabel>();
        vertexLabels.add(vertexLabel);
        when(vertexLabelRepository.findVertexLabelsByCodeIdAndVertexId(1, 1)).thenReturn(vertexLabels);

        ResponseVO responseVO = labelBL.getOneVertexLabels(vertexLabelVO);
        Assert.assertEquals(((ArrayList<VertexLabel>)responseVO.getContent()).get(0).getContent(), "rua");
    }

    @Test
    public void TestGetOneEdgeLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);

        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "title","rua");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "title","rua");
        List<EdgeLabel> edgeLabels = new ArrayList<EdgeLabel>();
        edgeLabels.add(edgeLabel);
        when(edgeLabelRepository.findEdgeLabelsByCodeIdAndEdgeId(1, 1)).thenReturn(edgeLabels);

        ResponseVO responseVO = labelBL.getOneEdgeLabels(edgeLabelVO);
        Assert.assertEquals(((ArrayList<EdgeLabel>)responseVO.getContent()).get(0).getContent(), "rua");
    }

    @Test
    public void TestGetOneDomainLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);


        DomainLabelVO domainLabelVO = new DomainLabelVO(1, 1, 1, 1, "title","rua");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1,"title", "rua");
        List<DomainLabel> domainLabels = new ArrayList<DomainLabel>();
        domainLabels.add(domainLabel);
        when(domainLabelRepository.findDomainLabelsByCodeIdAndFirstEdgeIdAndNumOfVertex(1, 1, 1)).thenReturn(domainLabels);

        ResponseVO responseVO = labelBL.getOneDomainLabels(domainLabelVO);
        Assert.assertEquals(((List<DomainLabel>)responseVO.getContent()).get(0).getContent(), "rua");
    }

//    @Test
//    public void TestGetAllVertexLabel() {
//        LabelBLImpl labelBL = new LabelBLImpl();
//        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
//        labelBL.setVertexLabelRepository(vertexLabelRepository);
//
//        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
//        ArrayList<VertexLabel> vertexLabelArrayList = new ArrayList<>();
//        VertexLabel vertexLabel = new VertexLabel(1, 1, 1,"title", "rua");
//        vertexLabelArrayList.add(vertexLabel);
//        when(vertexLabelRepository.findVertexLabelsByCodeIdAndUserId(1,1)).thenReturn(vertexLabelArrayList);
//
//        ResponseVO responseVO = labelBL.getAllVertexLabel(userAndCodeForm);
//        Assert.assertEquals(((ArrayList<VertexLabel>) responseVO.getContent()).size(), 1);
//    }
//
//    @Test
//    public void TestGetAllEdgeLabel() {
//        LabelBLImpl labelBL = new LabelBLImpl();
//        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
//        labelBL.setEdgeLabelRepository(edgeLabelRepository);
//
//        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
//        ArrayList<EdgeLabel> edgeLabelArrayList = new ArrayList<>();
//        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1,"title", "rua");
//        edgeLabelArrayList.add(edgeLabel);
//        when(edgeLabelRepository.findEdgeLabelsByCodeIdAndUserId(1,1)).thenReturn(edgeLabelArrayList);
//
//        ResponseVO responseVO = labelBL.getAllEdgeLabel(userAndCodeForm);
//        Assert.assertEquals(((ArrayList<EdgeLabel>) responseVO.getContent()).size(), 1);
//    }
//
//    @Test
//    public void TestGetAllDomainLabel() {
//        LabelBLImpl labelBL = new LabelBLImpl();
//        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
//        labelBL.setDomainLabelRepository(domainLabelRepository);
//
//        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
//        ArrayList<DomainLabel> domainLabelArrayList = new ArrayList<>();
//        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1,"title", "rua");
//        domainLabelArrayList.add(domainLabel);
//        when(domainLabelRepository.findDomainLabelsByCodeIdAndUserId(1,1)).thenReturn(domainLabelArrayList);
//
//        ResponseVO responseVO = labelBL.getAllDomainLabel(userAndCodeForm);
//        Assert.assertEquals(((ArrayList<DomainLabel>) responseVO.getContent()).size(), 1);
//    }
}
