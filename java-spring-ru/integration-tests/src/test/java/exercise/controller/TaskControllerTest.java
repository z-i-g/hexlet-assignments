package exercise.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// END
@SpringBootTest
@AutoConfigureMockMvc
// BEGIN
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Task task;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private Faker faker;

    @BeforeEach
    public void setUp() {
        task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .create();
    }

    @AfterEach
    public void destroy() {
        taskRepository.deleteAll();
    }

    @Test
    public void testIndex() throws Exception {
        taskRepository.save(task);
        mockMvc.perform(get("/tasks/{id}", task.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(task)));
    }

    @Test
    public void testCreate() throws Exception {
        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(task));

        var result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();

        var createdTask = taskRepository.findById(om.readValue(result.getResponse().getContentAsString(), Task.class).getId()).get();
        assertThat(createdTask.getTitle()).isEqualTo((task.getTitle()));
        assertThat(createdTask.getDescription()).isEqualTo((task.getDescription()));
    }

    @Test
    public void testUpdate() throws Exception {
        taskRepository.save(task);
        var data = new HashMap<>();
        data.put("title", "newTitle");
        data.put("description", "newDescription");

        var request = put("/tasks/{id}", task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var actualTask = taskRepository.findById(task.getId()).get();
        assertThat(actualTask.getTitle()).isEqualTo(("newTitle"));
        assertThat(actualTask.getDescription()).isEqualTo(("newDescription"));

        assertThatJson("{\"title\":\"newTitle\", \"description\":\"newDescription\"}").and(
                a -> a.node("title").isEqualTo("newTitle"),
                a -> a.node("description").isEqualTo("newDescription")
        );
    }

    @Test
    public void testDelete() throws Exception {
        taskRepository.save(task);
        mockMvc.perform(delete("/tasks/{id}", task.getId()))
                .andExpect(status().isOk());

        assertThat(taskRepository.findAll()).isEmpty();
    }
    // END
}
