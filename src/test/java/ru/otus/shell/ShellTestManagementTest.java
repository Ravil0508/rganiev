package ru.otus.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.service.ProcessQuestionService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты сервиса ShellTestManagement")
class ShellTestManagementTest {

    @Mock
    private ProcessQuestionService processQuestionService;

    @InjectMocks
    private ShellTestManagement shellTestManagement;

    @Test
    @DisplayName("Должен вызвать метод runTest у сервиса при выполнении команды start")
    void shouldCallRunTestWhenStartCommandExecuted() {
        doNothing().when(processQuestionService).runTest();

        shellTestManagement.start();

        verify(processQuestionService, times(1)).runTest();
        verifyNoMoreInteractions(processQuestionService);
    }
}
