package it.tino.blog.rssfeed;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import it.tino.blog.shared.GlobalExceptionHandler;
import it.tino.blog.shared.PageRequest;
import it.tino.blog.shared.PageResult;

public class RssFeedControllerTest {

    private RssFeedRepository rssFeedRepository;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        rssFeedRepository = Mockito.mock(RssFeedRepository.class);
        RssFeedDtoMapper rssFeedDtoMapper = Mockito.mock(RssFeedDtoMapper.class);

        RssFeedController controller = new RssFeedController(rssFeedRepository, rssFeedDtoMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void shouldRejectInvalidPageRequest() throws Exception {
        mockMvc.perform(get("/rss/feeds").param("size", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));

        mockMvc.perform(get("/rss/feeds").param("page", "-1"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/rss/feeds").param("size", "" + (PageRequest.MAX_SIZE + 1)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(rssFeedRepository);
    }

    @Test
    public void shouldAcceptValidPageRequest() throws Exception {
        Mockito.when(rssFeedRepository.findPage(ArgumentMatchers.any()))
                .thenReturn(PageResult.of(List.of(), 0, PageRequest.DEFAULT_SIZE, 0));

        mockMvc.perform(get("/rss/feeds")).andExpect(status().isOk());

        Mockito.verify(rssFeedRepository)
                .findPage(PageRequest.of(0, PageRequest.DEFAULT_SIZE));
    }
}
