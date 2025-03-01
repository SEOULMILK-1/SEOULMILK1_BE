package SeoulMilk1_BE.user.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Team {
    HQ("서울우유협동조합"),
    CS_TP("서울우유태평고객센터"),
    CS_YI("서울우유용인고객센터"),
    CS_GJ("서울우유광주고객센터"),
    CS_BM("서울우유보문고객센터"),
    ;

    private final String description;
}
