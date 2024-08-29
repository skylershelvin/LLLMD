package FileControllerTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.revature.LLL.FileManagement.FileController.FileController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file"))
                .andExpect(status().isOk())
                .andExpect(view().name("uploadPage"));
    }

    @Test
    public void testSaveData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file/saveData"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

}
