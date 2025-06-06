package net.whgkswo.excuse_bundle.entities.members.nicknames;

import lombok.RequiredArgsConstructor;
import net.whgkswo.excuse_bundle.entities.members.core.entitiy.Member;
import net.whgkswo.excuse_bundle.entities.members.core.repositoriy.MemberRepository;
import net.whgkswo.excuse_bundle.exceptions.BusinessLogicException;
import net.whgkswo.excuse_bundle.exceptions.ExceptionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NicknameService {
    private final MemberRepository memberRepository;

    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 10;

    // 닉네임 길이 검사
    private boolean isNicknameLengthValid(String nickname){
        return nickname.length() >= MIN_NICKNAME_LENGTH && nickname.length() <= MAX_NICKNAME_LENGTH;
    }

    // 닉네임 중복 검사
    private boolean isNicknameUnique(String nickname){
        List<Member> members = memberRepository.findAll();

        for (Member member : members){
            if(member.getNickname().equals(nickname)) return false;
        }
        return true;
    }

    // 닉네임 형식 검증
    private boolean isNicknameValid(String nickname) {
        return nickname.matches("^[가-힣a-zA-Z0-9]+$"); // 한글(완성형), 영문, 숫자만 가능
    }

    // 닉네임 종합 검증
    public void validateNickname(String nickname){
        boolean isLengthValid = isNicknameLengthValid(nickname);
        if(!isLengthValid) throw new BusinessLogicException(
                ExceptionType.nicknameLengthInvalid(MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH)
                );

        boolean isUnique = isNicknameUnique(nickname);
        if(!isUnique) throw new BusinessLogicException(ExceptionType.DUPLICATED_EMAIL);

        boolean isValid = isNicknameValid(nickname);
        if(!isValid) throw new BusinessLogicException(ExceptionType.WRONG_CHARACTER_IN_NICKNAME);
    }
}
