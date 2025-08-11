package home.hmvueblog.domain.common.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum YNCode {

    NULL("", "미지정"),
    Y("Y", "예"),
    N("N", "아니오");

    private final String code;
    private final String label;

    YNCode(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() { return code; }
    public String getLabel() { return label; }

    public static YNCode fromCode(String code) {
        for (YNCode status : values()) {
            if (status.code.equals(code)) return status;
        }
        throw new IllegalArgumentException("유효하지 않은 코드 값: " + code);
    }

    @Converter(autoApply = false) // 필요 시 true
    public static class ConverterImpl implements AttributeConverter<YNCode, String> {

        @Override
        public String convertToDatabaseColumn(YNCode attribute) {
            return attribute != null ? attribute.getCode() : null;
        }

        @Override
        public YNCode convertToEntityAttribute(String dbData) {
            return dbData != null ? YNCode.fromCode(dbData) : null;
        }
    }
}