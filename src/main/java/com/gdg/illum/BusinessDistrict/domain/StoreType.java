package com.gdg.illum.BusinessDistrict.domain;

public enum StoreType {
    NULL("NULL") {
        @Override
        public String getCode() {
            return null;
        }
    },
    RETAIL("소매업") {
        @Override
        public String getCode() {
            return "";
        }
    },
    LODGING("숙박업") {
        @Override
        public String getCode() {
            return "I1";
        }
    },
    RESTAURANT("음식점업") {
        @Override
        public String getCode() {
            return "I2";
        }
    },
    REAL_ESTIMATE("부동산업") {
        @Override
        public String getCode() {
            return "L1";
        }
    },
    PROFESSIONAL_SERVICE("전문 서비스업") {
        @Override
        public String getCode() {
            return "M1";
        }
    },
    RENTAL_SERVICE("지원 및 임대 서비스업") {
        @Override
        public String getCode() {
            return "N1";
        }
    },
    EDUCATION("교육") {
        @Override
        public String getCode() {
            return "P1";
        }
    },
    HEALTH("보건") {
        @Override
        public String getCode() {
            return "Q1";
        }
    },
    ART_SPORTS("예술 및 스포츠, 여가 관련 서비스업") {
        @Override
        public String getCode() {
            return "R1";
        }
    },
    REPAIR("수리 및 개인 서비스업") {
        @Override
        public String getCode() {
            return "S2";
        }
    };

    private final String koreanName;

    StoreType(String koreanName) {
        this.koreanName = koreanName;
    }

    public abstract String getCode();
}
