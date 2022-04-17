package baseball.game;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaseballTest {

    private final Baseball baseball = new Baseball();

    @Test
    public void 컴퓨터_랜덤_숫자생성() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method generateRandomNumbers = Baseball.class.getDeclaredMethod("generateRandomNumbers");
        generateRandomNumbers.setAccessible(true);
        List<Integer> computerRandomNumbers = (List<Integer>) generateRandomNumbers.invoke(baseball);
        assertThat(computerRandomNumbers.size()).isEqualTo(3);
        for (Integer computerRandomNumber : computerRandomNumbers) {
            assertThat(computerRandomNumber).isGreaterThan(0);
            assertThat(computerRandomNumber).isLessThan(10);
        }
    }

    @Test
    public void 사용자_정답입력_유효한값() {
        String enteredValue = "456";
        baseball.checkEnteredValue(enteredValue, true);
    }

    @Test
    public void 사용자_정답입력_무효한값_자리수() {
        String enteredValue = "4564";
        assertThatThrownBy(() -> baseball.checkEnteredValue(enteredValue, true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 사용자_정답입력_무효한값_숫자_이외의값() {
        String enteredValue = "tes";
        assertThatThrownBy(() -> baseball.checkEnteredValue(enteredValue, true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 점수_테스트_낫싱() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method score = Baseball.class.getDeclaredMethod("score", int.class, int.class);
        score.setAccessible(true);
        boolean result = (boolean) score.invoke(baseball, 0, 0);
        assertThat(result).isFalse();
    }

    @Test
    public void 점수_테스트_스트라이크() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method score = Baseball.class.getDeclaredMethod("score", int.class, int.class);
        score.setAccessible(true);
        boolean result = (boolean) score.invoke(baseball, 3, 0);
        assertThat(result).isTrue();
    }

    @Test
    public void 점수_테스트_기본() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method score = Baseball.class.getDeclaredMethod("score", int.class, int.class);
        score.setAccessible(true);
        boolean result = (boolean) score.invoke(baseball, 0, 1);
        assertThat(result).isFalse();
    }

    @Test
    public void 정답_테스트_오답() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isCorrect = Baseball.class.getDeclaredMethod("isCorrect", List.class, String.class);
        isCorrect.setAccessible(true);

        List<Integer> computer = Arrays.asList(1, 2, 3);
        String user = "321";
        boolean result = (boolean) isCorrect.invoke(baseball, computer, user);
        assertThat(result).isFalse();
    }

    @Test
    public void 정답_테스트_정답() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isCorrect = Baseball.class.getDeclaredMethod("isCorrect", List.class, String.class);
        isCorrect.setAccessible(true);

        List<Integer> computer = Arrays.asList(1, 2, 3);
        String user = "123";
        boolean result = (boolean) isCorrect.invoke(baseball, computer, user);
        assertThat(result).isTrue();
    }

    @Test
    public void 게임_재시작_여부_재시작() {
        boolean result = baseball.isRetry("1");
        assertThat(result).isTrue();
    }

    @Test
    public void 게임_재시작_여부_종료() {
        boolean result = baseball.isRetry("2");
        assertThat(result).isFalse();
    }

    @Test
    public void 게임_재시작_여부_무효값_입력() {
        assertThatThrownBy(() -> baseball.isRetry("3"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}