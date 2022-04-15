package baseball.game;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

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
        System.out.print("숫자를 입력해 주세요:");
        String inputValue = Console.readLine();
        boolean b = checkEnteredValue(inputValue);
        if (!b) throw new IllegalArgumentException();
        System.out.println(inputValue);
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
    private boolean checkEnteredValue(String enteredValue) {
        String reg = "\\d{3}";
        return Pattern.matches(reg, enteredValue.trim());
    }
}
