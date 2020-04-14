package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.blImpl.LabelBLImpl;
import com.old2dimension.OCEANIA.po.VertexLabel;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.VertexLabelVO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class LabelControllerTest {
    @Test
    public void noteVertexTest() throws Exception {
        LabelBLImpl labelBL = mock(LabelBLImpl.class);
        VertexLabelVO vertexLabelVO = new VertexLabelVO(1, 1, 1, null, "vertexLabel");
        VertexLabel vertexLabel = new VertexLabel(1, 1, 1, null, "vertexLabel");
        vertexLabel.setId(1);

        when(labelBL.noteVertex(vertexLabelVO)).thenReturn(ResponseVO.buildSuccess(vertexLabel));

        LabelController controller = new LabelController(labelBL);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/label/noteVertex")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1")
                .param("codeId", "1")
                .param("vertexId", "1")
                .param("content", "vertexLabel"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("{\n" +
                        "        \"id\": 2,\n" +
                        "        \"userId\": 1,\n" +
                        "        \"codeId\": 1,\n" +
                        "        \"vertexId\": 1,\n" +
                        "        \"content\": \"vertexLabel\",\n" +
                        "        \"title\": null\n" +
                        "    }"))
        ;

        verify(labelBL).noteVertex(vertexLabelVO);
    }
}
