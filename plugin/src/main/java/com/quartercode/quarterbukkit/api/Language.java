/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api;

public enum Language {
    AUSTRALIAN_ENGLISH ("Australian English", "en_AU"), US_ENGLISH ("US Englisch", "en_US"), AFRIKAANS ("Afrikaans", "af_ZA"), ARABIC ("العربية", "ar_SA"), BULGARIAN ("Български", "bg_BG"), CATALAN ("Català", "ca_ES"), CZECH ("Čeština", "cs_CZ"), CYMRAEG ("Cymraeg", "cy_GB"), DANISH ("Dansk", "da_DK"), GERMAN ("Deutsch", "de_DE"), GREEK ("Ελληνικά", "el_GR"), CANADIAN_ENGLISH ("Canadian English", "en_CA"), ENGLISH ("English", "en_GB"), PIRATE_SPEAK ("Pirate Speak", "en_PT"), ESPERANTO ("Esperanto", "eo_EO"), ARGENTINEAN_SPANISH ("Español Argentino", "es_AR"), SPANISH ("Español", "es_ES"), MEXICO_SPANISH ("Español México", "es_MX"), URUGUAY_SPANISH ("Español Uruguay", "es_UY"), VENEZUELA_SPANISH ("Español Venezuela", "es_VE"), ESTONIAN ("Eesti", "et_EE"), EUSKARA ("Euskara", "eu_ES"), ENGLISH1 ("زبان انگلیسی", "fa_IR"), FINNISH ("Suomi", "fi_FI"), TAGALOG ("Tagalog", "fil_PH"), FRENCH_CA ("Français", "fr_CA"), FRENCH ("Français", "fr_FR"), GAEILGE ("Gaeilge", "ga_IE"), GALICIAN ("Galego", "gl_ES"), HEBREW ("עברית", "he_IL"), ENGLISH2 ("अंग्रेज़ी", "hi_IN"), CROATIAN ("Hrvatski", "hr_HR"), HUNGARIAN ("Magyar", "hu_HU"), ARMENIAN ("Հայերեն", "hy_AM"), BAHASA_INDONESIA ("Bahasa Indonesia", "id_ID"), ICELANDIC ("Íslenska", "is_IS"), ITALIAN ("Italiano", "it_IT"), JAPANESE ("日本語", "ja_JP"), GEORGIAN ("ქართული", "ka_GE"), KOREAN ("한국어", "ko_KR"), KERNEWEK ("Kernewek", "kw_GB"), ENGLISH3 ("अंग्रेज़ी", "ky_KG"), LINGUA_LATINA ("Lingua latina", "la_LA"), LETZEBUERGESCH ("Lëtzebuergesch", "lb_LU"), LITHUANIAN ("Lietuvių", "lt_LT"), LATVIAN ("Latviešu", "lv_LV"), MALAY_NZ ("Bahasa Melayu", "mi_NZ"), MALAY_MY ("Bahasa Melayu", "ms_MY"), MALTI ("Malti", "mt_MT"), NORWEGIAN ("Norsk", "nb_NO"), DUTCH ("Nederlands", "nl_NL"), NORWEGIAN_NYNORSK ("Norsk nynorsk", "nn_NO"), NORWEGIAN1 ("Norsk", "no_NO"), OCCITAN ("Occitan", "oc_FR"), PORTUGUESE_BR ("Português", "pt_BR"), PORTUGUESE_PT ("Português", "pt_PT"), QUENYA ("Quenya", "qya_AA"), ROMANIAN ("Română", "ro_RO"), RUSSIAN ("Русский", "ru_RU"), ENGLISH4 ("Angličtina", "sk_SK"), SLOVENIAN ("Slovenščina", "sl_SI"), SERBIAN ("Српски", "sr_SP"), SWEDISH ("Svenska", "sv_SE"), THAI ("ภาษาไทย", "th_TH"), tlhIngan_Hol ("tlhIngan Hol", "tlh_AA"), TURKISH ("Türkçe", "tr_TR"), UKRAINIAN ("Українська", "uk_UA"), VIETNAMESE ("Tiếng Việt", "vi_VI"), SIMPLIFIED_CHINESE ("简体中文", "zh_CN"), TRADITIONAL_CHINESE ("繁體中文", "zh_TW"), POLISH ("Polski", "pl_PL"),

    UNKNOWN ("Unknown Language", "unknown");

    private String name;
    private String code;

    Language(String name, String code) {

        this.name = name;
        this.code = code;
    }

    public String getName() {

        return name;
    }

    public String getCode() {

        return code;
    }

    public static Language getByCode(String code) {

        for (Language l : values()) {
            if (l.getCode().equalsIgnoreCase(code)) {
                return l;
            }
        }
        return UNKNOWN;
    }
}
