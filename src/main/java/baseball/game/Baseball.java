package baseball.game;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Baseball {

    private int count = 0;

    /**
     * 사용자가 게임을 하기 위한 콘솔
     */
    public void gameStart() {
        List<Integer> test = Arrays.asList(1, 3, 5);
        if (count == 1) test = Arrays.asList(5, 0, 8);
        userInterface(test);
    }

    /**
     * 사용자 인터페이스
     *
     * @param answer 컴퓨터 3자리 숫자
     */
    private void userInterface(List<Integer> answer) {
        while (true) {
            System.out.print("숫자를 입력해 주세요:");
            String userInputValue = Console.readLine().trim();
            System.out.println(userInputValue);
            isNumber(userInputValue);

            if (userInputValue.length() == 1) {
                if (isRetry(userInputValue)) this.gameStart();
                System.out.println("게임 종료");
                break;
            }

            if (isCorrect(answer, userInputValue)) {
                System.out.println("게임을 새로 시작하려면 1, 종료 하려면 2를 입력하세요.");
                ++count;
                this.gameStart();
                break;
            }
        }
    }

    /**
     * 게임 재시작 여부
     * @apiNote Junit 테스트시에 InvocationTargetException 로 잡혀서 public 으로 변경
     * @param userEnteredValue 재시작 여부(1 재시작 or 2 종료)
     * @return boolean
     */
    public boolean isRetry(String userEnteredValue) {
        String reg = "[1-2]$";
        if (!Pattern.matches(reg, userEnteredValue)) throw new IllegalArgumentException();
        return userEnteredValue.equals("1");
    }

    /**
     * 상대방(컴퓨터)의 수를 생성한다.
     * @return 1부터 9까지 서로 다른 수로 이루어진 3자리 수
     */
    private List<Integer> generateRandomNumbers() {
        int minNumber = 1;
        int maxNumber = 9;
        int numberCount = 3;
        return Randoms.pickUniqueNumbersInRange(minNumber, maxNumber, numberCount);
    }

    /**
     * 사용자가 입력한 값을 확인(0~9 세자리 숫자만 허용)
     * @apiNote Junit 테스트시에 InvocationTargetException 로 잡혀서 public 으로 변경
     * @param enteredValue 사용자가 입력한 값
     */
    public void checkEnteredValue(String enteredValue, boolean isGameNumber) {
        String reg = "\\d{3}";
        if (!isGameNumber) reg = "[1-2]$";
        if (!Pattern.matches(reg, enteredValue.trim())) throw new IllegalArgumentException();
    }

    public List<Integer> stringToGameNumber(String enteredValue) {
        return Arrays.asList(
                Integer.parseInt(String.valueOf(enteredValue.charAt(0))),
                Integer.parseInt(String.valueOf(enteredValue.charAt(1))),
                Integer.parseInt(String.valueOf(enteredValue.charAt(2))));

    }

    public void isNumber(String enteredValue) {
        if (!Pattern.matches("^\\d*$", enteredValue.trim())) throw new IllegalArgumentException();
    }

    /**
     * 컴퓨터 값과 사용자 값을 비교하여 결과를 출력
     *
     * @param computer 컴퓨터 값
     * @param enteredValue 콘솔 입력값
     */
    private boolean isCorrect(List<Integer> computer, String enteredValue) {
        checkEnteredValue(enteredValue, true);
        List<Integer> user = stringToGameNumber(enteredValue);
        int strike = 0;
        int ball = 0;
        for (int i = 0; i < computer.size(); i++) {
            for (int j = 0; j < user.size(); j++) {
                if (computer.get(i).equals(user.get(j))) {
                    if (i == j) {
                        strike++;
                        continue;
                    }
                    ball++;
                }
            }
        }
        return score(strike, ball);
    }

    /**
     * 점수를 계산
     * @param strike 스트라이크
     * @param ball 볼
     * @return boolean
     */
    private boolean score(int strike, int ball) {
        if (strike == 0 && ball == 0) {
            System.out.println("낫싱");
            return false;
        }
        if (strike == 3) {
            System.out.println("3스트라이크");
            return true;
        }
        System.out.println(ball + "볼 " + strike + "스트라이크");
        return false;
    }

}
