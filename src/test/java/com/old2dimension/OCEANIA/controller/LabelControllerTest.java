package com.old2dimension.OCEANIA.controller;

import com.alibaba.fastjson.JSONObject;
import com.old2dimension.OCEANIA.blImpl.LabelBLImpl;
import com.old2dimension.OCEANIA.po.DomainLabel;
import com.old2dimension.OCEANIA.po.EdgeLabel;
import com.old2dimension.OCEANIA.po.VertexLabel;
import com.old2dimension.OCEANIA.vo.DomainLabelVO;
import com.old2dimension.OCEANIA.vo.EdgeLabelVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.VertexLabelVO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class LabelControllerTest {
    @Test
    public void noteVertexTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "test", "vertexLabel");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "test", "vertexLabel");
        vertexLabel.setId(1);

        when(labelBL.noteVertex(vertexLabelVO)).thenReturn(ResponseVO.buildSuccess(vertexLabel));

        String requestJson = JSONObject.toJSONString(vertexLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/noteVertex")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.codeId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.vertexId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.content").value("vertexLabel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.title").value("test"))
        ;

        verify(labelBL).noteVertex(vertexLabelVO);
    }

    @Test
    public void noteVertexTest2() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "test", "vertexLabel");
        vertexLabelVO.setId(1);

        when(labelBL.noteVertex(vertexLabelVO)).thenReturn(ResponseVO.buildFailure("the vertex label to update does not exist"));

        String requestJson = JSONObject.toJSONString(vertexLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/noteVertex")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the vertex label to update does not exist"))
                ;

        verify(labelBL).noteVertex(vertexLabelVO);
    }

    @Test
    public void noteEdgeTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "test", "edgeLabel");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "test", "edgeLabel");
        edgeLabel.setId(1);

        when(labelBL.noteEdge(edgeLabelVO)).thenReturn(ResponseVO.buildSuccess(edgeLabel));

        String requestJson = JSONObject.toJSONString(edgeLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/noteEdge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.codeId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.edgeId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.content").value("edgeLabel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.title").value("test"))
        ;

        verify(labelBL).noteEdge(edgeLabelVO);
    }

    @Test
    public void noteDomainTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        DomainLabelVO domainLabelVO = new DomainLabelVO(1, 1, 1, 1, "test", "domainLabel");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "test", "domainLabel");
        domainLabel.setId(1);

        when(labelBL.noteDomain(domainLabelVO)).thenReturn(ResponseVO.buildSuccess(domainLabel));

        String requestJson = JSONObject.toJSONString(domainLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/noteDomain")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.codeId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.content").value("domainLabel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.title").value("test"))
        ;

        verify(labelBL).noteDomain(domainLabelVO);
    }

    @Test
    public void deleteVertexLabelTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "test", "vertexLabel");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "test", "vertexLabel");
        vertexLabel.setId(1);

        when(labelBL.deleteVertexLabel(vertexLabelVO)).thenReturn(ResponseVO.buildSuccess("delete label successfully"));

        String requestJson = JSONObject.toJSONString(vertexLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/deleteVertexLabel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("delete label successfully"))
        ;

        verify(labelBL).deleteVertexLabel(vertexLabelVO);
    }

    @Test
    public void deleteEdgeLabelTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "test", "edgeLabel");
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "test", "edgeLabel");
        edgeLabel.setId(1);

        when(labelBL.deleteEdgeLabel(edgeLabelVO)).thenReturn(ResponseVO.buildSuccess("delete label successfully"));

        String requestJson = JSONObject.toJSONString(edgeLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/deleteEdgeLabel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("delete label successfully"))
        ;

        verify(labelBL).deleteEdgeLabel(edgeLabelVO);
    }

    @Test
    public void deleteDomainLabelTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        DomainLabelVO domainLabelVO = new DomainLabelVO(1, 1, 1, 1, "test", "domainLabel");
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "test", "domainLabel");
        domainLabel.setId(1);

        when(labelBL.deleteDomainLabel(domainLabelVO)).thenReturn(ResponseVO.buildSuccess("delete label successfully"));

        String requestJson = JSONObject.toJSONString(domainLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/deleteDomainLabel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("delete label successfully"))
        ;

        verify(labelBL).deleteDomainLabel(domainLabelVO);
    }

    @Test
    public void getOneVertexLabelTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, "test", "vertexLabel");
        List<VertexLabel> vertexLabels = new ArrayList<>();
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, "test", "vertexLabel");
        vertexLabel.setId(1);
        vertexLabels.add(vertexLabel);

        when(labelBL.getOneVertexLabels(vertexLabelVO)).thenReturn(ResponseVO.buildSuccess(vertexLabels));

        String requestJson = JSONObject.toJSONString(vertexLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/getOneVertexLabel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].codeId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].vertexId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].content").value("vertexLabel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("test"))
        ;

        verify(labelBL).getOneVertexLabels(vertexLabelVO);
    }

    @Test
    public void getOneEdgeLabelTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        EdgeLabelVO edgeLabelVO = new EdgeLabelVO(1, 1, 1, "test", "edgeLabel");
        List<EdgeLabel> edgeLabels = new ArrayList<>();
        EdgeLabel edgeLabel = new EdgeLabel(1, 1, 1, "test", "edgeLabel");
        edgeLabel.setId(1);
        edgeLabels.add(edgeLabel);

        when(labelBL.getOneEdgeLabels(edgeLabelVO)).thenReturn(ResponseVO.buildSuccess(edgeLabels));

        String requestJson = JSONObject.toJSONString(edgeLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/getOneEdgeLabel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].codeId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].edgeId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].content").value("edgeLabel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("test"))
        ;

        verify(labelBL).getOneEdgeLabels(edgeLabelVO);
    }

    @Test
    public void getOneDomainLabelTest1() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        DomainLabelVO domainLabelVO = new DomainLabelVO(1, 1, 1, 1, "test", "domainLabel");
        List<DomainLabel> domainLabels = new ArrayList<>();
        DomainLabel domainLabel = new DomainLabel(1, 1, 1, 1, "test", "domainLabel");
        domainLabel.setId(1);
        domainLabels.add(domainLabel);

        when(labelBL.getOneDomainLabels(domainLabelVO)).thenReturn(ResponseVO.buildSuccess(domainLabels));

        String requestJson = JSONObject.toJSONString(domainLabelVO);
        LabelController controller = new LabelController();
        controller.setLabelBL(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/getOneDomainLabel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].codeId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].content").value("domainLabel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("test"))
        ;

        verify(labelBL).getOneDomainLabels(domainLabelVO);
    }

}
