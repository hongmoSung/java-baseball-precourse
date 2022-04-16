package baseball.game;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Baseball {

    /**
     * 사용자가 게임을 하기 위한 콘솔
     */
    public void gameStart() {
        List<Integer> answer = generateRandomNumbers();
        System.out.print("정답 -> ");
        for (Integer integer : answer) {
            System.out.print(integer);
        }
        System.out.println("\n");
        userInterface(answer);
    }

    /**
     * 사용자 인터페이스
     * @param answer 컴퓨터 3자리 숫자
     */
    private void userInterface(List<Integer> answer) {
        while (true) {
            System.out.print("숫자를 입력해 주세요:");
            String userInputValue = Console.readLine();
            List<Integer> integers = checkEnteredValue(userInputValue);
            if (!isCorrect(answer, integers)) continue;
            if (isRetry()) this.gameStart();
            System.out.println("게임이 종료 되었습니다.");
            break;
        }
    }

    /**
     * 게임 재시작 여부
     */
    private boolean isRetry() {
        String reg = "[1-2]$";
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        String userEnteredValue = Console.readLine().trim();
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
     * @param enteredValue 사용자가 입력한 값
     * @return boolean
     */
    private List<Integer> checkEnteredValue(String enteredValue) {
        String reg = "\\d{3}";
        if (!Pattern.matches(reg, enteredValue.trim())) throw new IllegalArgumentException();
        String trim = enteredValue.trim();
        return Arrays.asList(
                Integer.parseInt(String.valueOf(trim.charAt(0))),
                Integer.parseInt(String.valueOf(trim.charAt(1))),
                Integer.parseInt(String.valueOf(trim.charAt(2))));
    }

    /**
     * 컴퓨터 값과 사용자 값을 비교하여 결과를 출력
     * @param computer 컴퓨터 값
     * @param user 사용자 값
     */
    private boolean isCorrect(List<Integer> computer, List<Integer> user) {
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
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임종료");
            return true;
        }
        System.out.println(strike + " 스트라이크 " + ball + "볼입니다.");
        return false;
    }


}
