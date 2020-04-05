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
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
public class LabelBLImplTest {
    @Test
    public void TestNoteVertex() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "rua");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "rua");
        when(vertexLabelRepository.findVertexLabelByCodeIdAndVertexId(1, 1)).thenReturn(vertexLabel);
        when(vertexLabelRepository.save(vertexLabel)).thenReturn(vertexLabel);

        ResponseVO responseVO = labelBL.noteVertex(vertexLabelVO);
        Assert.assertEquals(((VertexLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestNoteEdge() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);

        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1,1,1,"rua");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "rua");
        when(edgeLabelRepository.findEdgeLabelByCodeIdAndEdgeId(1, 1)).thenReturn(edgeLabel);
        when(edgeLabelRepository.save(edgeLabel)).thenReturn(edgeLabel);

        ResponseVO responseVO = labelBL.noteEdge(edgeLabelVO);
        Assert.assertEquals(((EdgeLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestNoteDomain() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);

        DomainLabelVO domainLabelVO = new DomainLabelVO(1,1,1, 1,"rua");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "rua");
        when(domainLabelRepository.findDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(1, 1, 1)).thenReturn(domainLabel);
        when(domainLabelRepository.save(domainLabel)).thenReturn(domainLabel);

        ResponseVO responseVO = labelBL.noteDomain(domainLabelVO);
        Assert.assertEquals(((DomainLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestDeleteVertexLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "rua");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "rua");

        when(vertexLabelRepository.findVertexLabelByCodeIdAndVertexId(1, 1)).thenReturn(vertexLabel);
        doNothing().when(vertexLabelRepository).deleteVertexLabelByCodeIdAndVertexId(1, 1);
        ResponseVO responseVO = labelBL.deleteVertexLabel(vertexLabelVO);
        Assert.assertEquals(responseVO.getContent(), "delete label successfully");
    }

    @Test
    public void TestDeleteEdgeLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);

        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "rua");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "rua");

        when(edgeLabelRepository.findEdgeLabelByCodeIdAndEdgeId(1, 1)).thenReturn(edgeLabel);
        doNothing().when(edgeLabelRepository).deleteEdgeLabelByCodeIdAndEdgeId(1, 1);
        ResponseVO responseVO = labelBL.deleteEdgeLabel(edgeLabelVO);
        Assert.assertEquals(responseVO.getContent(), "delete label successfully");
    }

    @Test
    public void TestDeleteDomainLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);

        DomainLabelVO domainLabelVO = new DomainLabelVO(1, 1, 1, 1, "rua");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "rua");

        when(domainLabelRepository.findDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(1, 1, 1)).thenReturn(domainLabel);
        doNothing().when(domainLabelRepository).deleteDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(1, 1, 1);
        ResponseVO responseVO = labelBL.deleteDomainLabel(domainLabelVO);
        Assert.assertEquals(responseVO.getContent(), "delete label successfully");
    }

    @Test
    public void TestGetOneVertexLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "rua");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "rua");
        when(vertexLabelRepository.findVertexLabelByCodeIdAndVertexId(1, 1)).thenReturn(vertexLabel);

        ResponseVO responseVO = labelBL.getOneVertexLabel(vertexLabelVO);
        Assert.assertEquals(((VertexLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestGetOneEdgeLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);

        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "rua");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "rua");
        when(edgeLabelRepository.findEdgeLabelByCodeIdAndEdgeId(1, 1)).thenReturn(edgeLabel);

        ResponseVO responseVO = labelBL.getOneEdgeLabel(edgeLabelVO);
        Assert.assertEquals(((EdgeLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestGetOneDomainLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);

        DomainLabelVO domainLabelVO = new DomainLabelVO(1, 1, 1, 1, "rua");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "rua");
        when(domainLabelRepository.findDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(1, 1, 1)).thenReturn(domainLabel);

        ResponseVO responseVO = labelBL.getOneDomainLabel(domainLabelVO);
        Assert.assertEquals(((DomainLabel)responseVO.getContent()).getContent(), "rua");
    }

    @Test
    public void TestGetAllVertexLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        VertexLabelRepository vertexLabelRepository = mock(VertexLabelRepository.class);
        labelBL.setVertexLabelRepository(vertexLabelRepository);

        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
        ArrayList<VertexLabel> vertexLabelArrayList = new ArrayList<>();
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "rua");
        vertexLabelArrayList.add(vertexLabel);
        when(vertexLabelRepository.findVertexLabelsByCodeId(1)).thenReturn(vertexLabelArrayList);

        ResponseVO responseVO = labelBL.getAllVertexLabel(userAndCodeForm);
        Assert.assertEquals(((ArrayList<VertexLabel>) responseVO.getContent()).size(), 1);
    }

    @Test
    public void TestGetAllEdgeLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        EdgeLabelRepository edgeLabelRepository = mock(EdgeLabelRepository.class);
        labelBL.setEdgeLabelRepository(edgeLabelRepository);

        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
        ArrayList<EdgeLabel> edgeLabelArrayList = new ArrayList<>();
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "rua");
        edgeLabelArrayList.add(edgeLabel);
        when(edgeLabelRepository.findEdgeLabelsByCodeId(1)).thenReturn(edgeLabelArrayList);

        ResponseVO responseVO = labelBL.getAllEdgeLabel(userAndCodeForm);
        Assert.assertEquals(((ArrayList<EdgeLabel>) responseVO.getContent()).size(), 1);
    }

    @Test
    public void TestGetAllDomainLabel() {
        LabelBLImpl labelBL = new LabelBLImpl();
        DomainLabelRepository domainLabelRepository = mock(DomainLabelRepository.class);
        labelBL.setDomainLabelRepository(domainLabelRepository);

        UserAndCodeForm userAndCodeForm = new UserAndCodeForm(1, 1);
        ArrayList<DomainLabel> domainLabelArrayList = new ArrayList<>();
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "rua");
        domainLabelArrayList.add(domainLabel);
        when(domainLabelRepository.findDomainLabelsByCodeId(1)).thenReturn(domainLabelArrayList);

        ResponseVO responseVO = labelBL.getAllDomainLabel(userAndCodeForm);
        Assert.assertEquals(((ArrayList<DomainLabel>) responseVO.getContent()).size(), 1);
    }
}
