import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;


class HorseTest {

    @Test
    public void constructor_NullNameParamPassed_ThrowsIllegalArgumentException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\n\n", "\t", "\t\t", "\t \t"})
    public void constructor_EmptyNameParamPassed_ThrowsIllegalArgumentException(String name) {
        // Arrange
        String expectedMessage = "Name cannot be blank.";

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_NegativeSpeedParamPassed_ThrowsIllegalArgumentException() {
        String expectedMessage = "Speed cannot be negative.";
        String name = "TestName";
        double speed = -5;
        double distance = 2;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_NegativeDistanceParamPassed_ThrowsIllegalArgumentException() {
        String expectedMessage = "Distance cannot be negative.";
        String name = "TestName";
        double speed = 5;
        double distance = -2;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getName_ReturnsCorrectName() {
        String name = "TestName";
        double speed = 5;
        double distance = 2;
        Horse horse = new Horse(name, speed, distance);

        String actualName = horse.getName();

        assertEquals(name, actualName);
    }

    @Test
    void getSpeed_ReturnsCorrectSpeed() {
        String name = "TestName";
        double speed = 5;
        double distance = 2;
        Horse horse = new Horse(name, speed, distance);

        double actualSpeed = horse.getSpeed();

        assertEquals(speed, actualSpeed);
    }

    @Test
    void getDistance_ReturnsCorrectDistance() {
        String name = "TestName";
        double speed = 2;
        double distance = 4;
        Horse horse = new Horse(name, speed, distance);

        double actualDistance = horse.getDistance();

        assertEquals(distance, actualDistance);
    }

    @Test
    void move_CallsGetRandomDoubleMethodWithCorrectParams() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("TestName", 1, 2);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.5, 0.8, 15, 0, 153})
    public void moveUsedFormulaIsCorrect(double fakeRandomValue) {
        double min = 0.2;
        double max = 0.9;
        String name = "TestName";
        double speed = 2.5;
        double distance = 250;
        Horse horse = new Horse(name, speed, distance);
        double expectedDistance = distance + speed * fakeRandomValue;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeRandomValue);

            horse.move();
        }

        assertEquals(expectedDistance, horse.getDistance());
    }

}