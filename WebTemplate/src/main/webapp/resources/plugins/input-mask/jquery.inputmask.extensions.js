/*
Input Mask plugin extensions
http://github.com/RobinHerbots/jquery.inputmask
Copyright (c) 2010 - 2014 Robin Herbots
Licensed under the MIT license (http://www.opensource.org/licenses/mit-license.php)
Version: 0.0.0

Optional extensions on the jquery.inputmask base
 */
(function($) {
    // extra definitions
    $.extend($.inputmask.defaults.definitions, {
        'A' : {
            validator : "[A-Za-z]",
            cardinality : 1,
            casing : "upper" // auto uppercasing
        },
        '#' : {
            validator : "[A-Za-z\u0410-\u044F\u0401\u04510-9]",
            cardinality : 1,
            casing : "upper"
        }
    });
    $
            .extend(
                    $.inputmask.defaults.aliases,
                    {
                        'url' : {
                            mask : "ir",
                            placeholder : "",
                            separator : "",
                            defaultPrefix : "http://",
                            regex : {
                                urlpre1 : new RegExp("[fh]"),
                                urlpre2 : new RegExp("(ft|ht)"),
                                urlpre3 : new RegExp("(ftp|htt)"),
                                urlpre4 : new RegExp("(ftp:|http|ftps)"),
                                urlpre5 : new RegExp(
                                        "(ftp:/|ftps:|http:|https)"),
                                urlpre6 : new RegExp(
                                        "(ftp://|ftps:/|http:/|https:)"),
                                urlpre7 : new RegExp(
                                        "(ftp://|ftps://|http://|https:/)"),
                                urlpre8 : new RegExp(
                                        "(ftp://|ftps://|http://|https://)")
                            },
                            definitions : {
                                'i' : {
                                    validator : function(chrs, buffer, pos,
                                            strict, opts) {
                                        return true;
                                    },
                                    cardinality : 8,
                                    prevalidator : (function() {
                                        var result = [], prefixLimit = 8;
                                        for (var i = 0; i < prefixLimit; i++) {
                                            result[i] = (function() {
                                                var j = i;
                                                return {
                                                    validator : function(chrs,
                                                            buffer, pos,
                                                            strict, opts) {
                                                        if (opts.regex["urlpre"
                                                                + (j + 1)]) {
                                                            var tmp = chrs, k;
                                                            if (((j + 1) - chrs.length) > 0) {
                                                                tmp = buffer
                                                                        .join(
                                                                                '')
                                                                        .substring(
                                                                                0,
                                                                                ((j + 1) - chrs.length))
                                                                        + ""
                                                                        + tmp;
                                                            }
                                                            var isValid = opts.regex["urlpre"
                                                                    + (j + 1)]
                                                                    .test(tmp);
                                                            if (!strict
                                                                    && !isValid) {
                                                                pos = pos - j;
                                                                for (k = 0; k < opts.defaultPrefix.length; k++) {
                                                                    buffer[pos] = opts.defaultPrefix[k];
                                                                    pos++;
                                                                }
                                                                for (k = 0; k < tmp.length - 1; k++) {
                                                                    buffer[pos] = tmp[k];
                                                                    pos++;
                                                                }
                                                                return {
                                                                    "pos" : pos
                                                                };
                                                            }
                                                            return isValid;
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                    cardinality : j
                                                };
                                            })();
                                        }
                                        return result;
                                    })()
                                },
                                "r" : {
                                    validator : ".",
                                    cardinality : 50
                                }
                            },
                            insertMode : false,
                            autoUnmask : false
                        },
                        "ip" : { // ip-address mask
                            mask : [ "[[x]y]z.[[x]y]z.[[x]y]z.x[yz]",
                                    "[[x]y]z.[[x]y]z.[[x]y]z.[[x]y][z]" ],
                            definitions : {
                                'x' : {
                                    validator : "[012]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'y' : {
                                    validator : function(chrs, buffer, pos,
                                            strict, opts) {
                                        if (pos - 1 > -1
                                                && buffer[pos - 1] != ".")
                                            chrs = buffer[pos - 1] + chrs;
                                        else
                                            chrs = "0" + chrs;
                                        return new RegExp("2[0-5]|[01][0-9]")
                                                .test(chrs);
                                    },
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'z' : {
                                    validator : function(chrs, buffer, pos,
                                            strict, opts) {
                                        if (pos - 1 > -1
                                                && buffer[pos - 1] != ".") {
                                            chrs = buffer[pos - 1] + chrs;
                                            if (pos - 2 > -1
                                                    && buffer[pos - 2] != ".") {
                                                chrs = buffer[pos - 2] + chrs;
                                            } else
                                                chrs = "0" + chrs;
                                        } else
                                            chrs = "00" + chrs;
                                        return new RegExp(
                                                "25[0-5]|2[0-4][0-9]|[01][0-9][0-9]")
                                                .test(chrs);
                                    },
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                }
                            }
                        },
                        "ipv4_subnet" : {
                            mask : [ "[[x]y]z.[[x]y]z.[[x]y]z.x[yz]/l[m]",
                                    "[[x]y]z.[[x]y]z.[[x]y]z.[[x]y][z]/l[m]",
                                    "[[x]y]z.[[x]y]z.[[x]y]z.x[yz]/k[j]",
                                    "[[x]y]z.[[x]y]z.[[x]y]z.[[x]y][z]/k[j]",
                                    "[[x]y]z.[[x]y]z.[[x]y]z.x[yz]/j",
                                    "[[x]y]z.[[x]y]z.[[x]y]z.[[x]y][z]/j" ],
                            definitions : {
                                'x' : {
                                    validator : "[012]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'y' : {
                                    validator : function(chrs, buffer, pos,
                                            strict, opts) {
                                        if (pos - 1 > -1
                                                && buffer[pos - 1] != ".")
                                            chrs = buffer[pos - 1] + chrs;
                                        else
                                            chrs = "0" + chrs;
                                        return new RegExp("2[0-5]|[01][0-9]")
                                                .test(chrs);
                                    },
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'z' : {
                                    validator : function(chrs, buffer, pos,
                                            strict, opts) {
                                        if (pos - 1 > -1
                                                && buffer[pos - 1] != ".") {
                                            chrs = buffer[pos - 1] + chrs;
                                            if (pos - 2 > -1
                                                    && buffer[pos - 2] != ".") {
                                                chrs = buffer[pos - 2] + chrs;
                                            } else
                                                chrs = "0" + chrs;
                                        } else
                                            chrs = "00" + chrs;
                                        return new RegExp(
                                                "25[0-5]|2[0-4][0-9]|[01][0-9][0-9]")
                                                .test(chrs);
                                    },
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'k' : {
                                    validator : "[1-2]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'j' : {
                                    validator : "[0-9]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'l' : {
                                    validator : "[3]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'm' : {
                                    validator : "[0-2]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                }
                            }
                        },
                        "ipv4" : {
                            mask : [ "[[x]y]z.[[x]y]z.[[x]y]z.x[yz]",
                                    "[[x]y]z.[[x]y]z.[[x]y]z.[[x]y][z]", ],
                            definitions : {
                                'x' : {
                                    validator : "[012]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'y' : {
                                    validator : function(chrs, buffer, pos,
                                            strict, opts) {
                                        if (pos - 1 > -1
                                                && buffer[pos - 1] != ".")
                                            chrs = buffer[pos - 1] + chrs;
                                        else
                                            chrs = "0" + chrs;
                                        return new RegExp("2[0-5]|[01][0-9]")
                                                .test(chrs);
                                    },
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'z' : {
                                    validator : function(chrs, buffer, pos,
                                            strict, opts) {
                                        if (pos - 1 > -1
                                                && buffer[pos - 1] != ".") {
                                            chrs = buffer[pos - 1] + chrs;
                                            if (pos - 2 > -1
                                                    && buffer[pos - 2] != ".") {
                                                chrs = buffer[pos - 2] + chrs;
                                            } else
                                                chrs = "0" + chrs;
                                        } else
                                            chrs = "00" + chrs;
                                        return new RegExp(
                                                "25[0-5]|2[0-4][0-9]|[01][0-9][0-9]")
                                                .test(chrs);
                                    },
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'k' : {
                                    validator : "[1-2]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'j' : {
                                    validator : "[0-9]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'l' : {
                                    validator : "[3]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'm' : {
                                    validator : "[0-2]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                }
                            }
                        },
                        /**
                         * ipv6 pattern make code static int MAX = 8; static
                         * LinkedList<String> testList = new LinkedList<>();
                         * public static void main(String[] args) throws
                         * Exception { makePattern("x"); makePattern("y");
                         * 
                         * for(String s: testList) { StringBuffer sb = new
                         * StringBuffer(); sb.append('"'); for (int i = 0; i <
                         * s.length(); i++) { if(i<s.length()-1) {
                         * sb.append(s.charAt(i)); sb.append(':'); } else {
                         * sb.append(s.charAt(i)); } }
                         * System.out.println(sb+"/j[j]\",");
                         * System.out.println(sb+"/kmj\",");
                         * System.out.println(sb+"/kzn\","); } }
                         * 
                         * public static String makePattern(String s) {
                         * if(s.length() == MAX) { testList.add(s); return null; }
                         * if(checkString(makePattern(addX(s)))){ return
                         * makePattern(addY(s)); } return makePattern(addX(s)); }
                         * public static boolean checkString (String s ) {
                         * for(String s2 : testList) { if (s2.equals(s)){ return
                         * false; } } return true; } public static String addX
                         * (String s) { s+="x"; return s; } public static String
                         * addY (String s) { s+="y"; return s; }
                         */
                        "ipv6" : {
                            mask : [ "x:x:x:x:x:x:x:x", "x:x:x:x:x:x:x:y",
                                    "x:x:x:x:x:x:y:x", "x:x:x:x:x:x:y:y",
                                    "x:x:x:x:x:y:x:x", "x:x:x:x:x:y:x:y",
                                    "x:x:x:x:x:y:y:x", "x:x:x:x:x:y:y:y",
                                    "x:x:x:x:y:x:x:x", "x:x:x:x:y:x:x:y",
                                    "x:x:x:x:y:x:y:x", "x:x:x:x:y:x:y:y",
                                    "x:x:x:x:y:y:x:x", "x:x:x:x:y:y:x:y",
                                    "x:x:x:x:y:y:y:x", "x:x:x:x:y:y:y:y",
                                    "x:x:x:y:x:x:x:x", "x:x:x:y:x:x:x:y",
                                    "x:x:x:y:x:x:y:x", "x:x:x:y:x:x:y:y",
                                    "x:x:x:y:x:y:x:x", "x:x:x:y:x:y:x:y",
                                    "x:x:x:y:x:y:y:x", "x:x:x:y:x:y:y:y",
                                    "x:x:x:y:y:x:x:x", "x:x:x:y:y:x:x:y",
                                    "x:x:x:y:y:x:y:x", "x:x:x:y:y:x:y:y",
                                    "x:x:x:y:y:y:x:x", "x:x:x:y:y:y:x:y",
                                    "x:x:x:y:y:y:y:x", "x:x:x:y:y:y:y:y",
                                    "x:x:y:x:x:x:x:x", "x:x:y:x:x:x:x:y",
                                    "x:x:y:x:x:x:y:x", "x:x:y:x:x:x:y:y",
                                    "x:x:y:x:x:y:x:x", "x:x:y:x:x:y:x:y",
                                    "x:x:y:x:x:y:y:x", "x:x:y:x:x:y:y:y",
                                    "x:x:y:x:y:x:x:x", "x:x:y:x:y:x:x:y",
                                    "x:x:y:x:y:x:y:x", "x:x:y:x:y:x:y:y",
                                    "x:x:y:x:y:y:x:x", "x:x:y:x:y:y:x:y",
                                    "x:x:y:x:y:y:y:x", "x:x:y:x:y:y:y:y",
                                    "x:x:y:y:x:x:x:x", "x:x:y:y:x:x:x:y",
                                    "x:x:y:y:x:x:y:x", "x:x:y:y:x:x:y:y",
                                    "x:x:y:y:x:y:x:x", "x:x:y:y:x:y:x:y",
                                    "x:x:y:y:x:y:y:x", "x:x:y:y:x:y:y:y",
                                    "x:x:y:y:y:x:x:x", "x:x:y:y:y:x:x:y",
                                    "x:x:y:y:y:x:y:x", "x:x:y:y:y:x:y:y",
                                    "x:x:y:y:y:y:x:x", "x:x:y:y:y:y:x:y",
                                    "x:x:y:y:y:y:y:x", "x:x:y:y:y:y:y:y",
                                    "x:y:x:x:x:x:x:x", "x:y:x:x:x:x:x:y",
                                    "x:y:x:x:x:x:y:x", "x:y:x:x:x:x:y:y",
                                    "x:y:x:x:x:y:x:x", "x:y:x:x:x:y:x:y",
                                    "x:y:x:x:x:y:y:x", "x:y:x:x:x:y:y:y",
                                    "x:y:x:x:y:x:x:x", "x:y:x:x:y:x:x:y",
                                    "x:y:x:x:y:x:y:x", "x:y:x:x:y:x:y:y",
                                    "x:y:x:x:y:y:x:x", "x:y:x:x:y:y:x:y",
                                    "x:y:x:x:y:y:y:x", "x:y:x:x:y:y:y:y",
                                    "x:y:x:y:x:x:x:x", "x:y:x:y:x:x:x:y",
                                    "x:y:x:y:x:x:y:x", "x:y:x:y:x:x:y:y",
                                    "x:y:x:y:x:y:x:x", "x:y:x:y:x:y:x:y",
                                    "x:y:x:y:x:y:y:x", "x:y:x:y:x:y:y:y",
                                    "x:y:x:y:y:x:x:x", "x:y:x:y:y:x:x:y",
                                    "x:y:x:y:y:x:y:x", "x:y:x:y:y:x:y:y",
                                    "x:y:x:y:y:y:x:x", "x:y:x:y:y:y:x:y",
                                    "x:y:x:y:y:y:y:x", "x:y:x:y:y:y:y:y",
                                    "x:y:y:x:x:x:x:x", "x:y:y:x:x:x:x:y",
                                    "x:y:y:x:x:x:y:x", "x:y:y:x:x:x:y:y",
                                    "x:y:y:x:x:y:x:x", "x:y:y:x:x:y:x:y",
                                    "x:y:y:x:x:y:y:x", "x:y:y:x:x:y:y:y",
                                    "x:y:y:x:y:x:x:x", "x:y:y:x:y:x:x:y",
                                    "x:y:y:x:y:x:y:x", "x:y:y:x:y:x:y:y",
                                    "x:y:y:x:y:y:x:x", "x:y:y:x:y:y:x:y",
                                    "x:y:y:x:y:y:y:x", "x:y:y:x:y:y:y:y",
                                    "x:y:y:y:x:x:x:x", "x:y:y:y:x:x:x:y",
                                    "x:y:y:y:x:x:y:x", "x:y:y:y:x:x:y:y",
                                    "x:y:y:y:x:y:x:x", "x:y:y:y:x:y:x:y",
                                    "x:y:y:y:x:y:y:x", "x:y:y:y:x:y:y:y",
                                    "x:y:y:y:y:x:x:x", "x:y:y:y:y:x:x:y",
                                    "x:y:y:y:y:x:y:x", "x:y:y:y:y:x:y:y",
                                    "x:y:y:y:y:y:x:x", "x:y:y:y:y:y:x:y",
                                    "x:y:y:y:y:y:y:x", "x:y:y:y:y:y:y:y",
                                    "y:x:x:x:x:x:x:x", "y:x:x:x:x:x:x:y",
                                    "y:x:x:x:x:x:y:x", "y:x:x:x:x:x:y:y",
                                    "y:x:x:x:x:y:x:x", "y:x:x:x:x:y:x:y",
                                    "y:x:x:x:x:y:y:x", "y:x:x:x:x:y:y:y",
                                    "y:x:x:x:y:x:x:x", "y:x:x:x:y:x:x:y",
                                    "y:x:x:x:y:x:y:x", "y:x:x:x:y:x:y:y",
                                    "y:x:x:x:y:y:x:x", "y:x:x:x:y:y:x:y",
                                    "y:x:x:x:y:y:y:x", "y:x:x:x:y:y:y:y",
                                    "y:x:x:y:x:x:x:x", "y:x:x:y:x:x:x:y",
                                    "y:x:x:y:x:x:y:x", "y:x:x:y:x:x:y:y",
                                    "y:x:x:y:x:y:x:x", "y:x:x:y:x:y:x:y",
                                    "y:x:x:y:x:y:y:x", "y:x:x:y:x:y:y:y",
                                    "y:x:x:y:y:x:x:x", "y:x:x:y:y:x:x:y",
                                    "y:x:x:y:y:x:y:x", "y:x:x:y:y:x:y:y",
                                    "y:x:x:y:y:y:x:x", "y:x:x:y:y:y:x:y",
                                    "y:x:x:y:y:y:y:x", "y:x:x:y:y:y:y:y",
                                    "y:x:y:x:x:x:x:x", "y:x:y:x:x:x:x:y",
                                    "y:x:y:x:x:x:y:x", "y:x:y:x:x:x:y:y",
                                    "y:x:y:x:x:y:x:x", "y:x:y:x:x:y:x:y",
                                    "y:x:y:x:x:y:y:x", "y:x:y:x:x:y:y:y",
                                    "y:x:y:x:y:x:x:x", "y:x:y:x:y:x:x:y",
                                    "y:x:y:x:y:x:y:x", "y:x:y:x:y:x:y:y",
                                    "y:x:y:x:y:y:x:x", "y:x:y:x:y:y:x:y",
                                    "y:x:y:x:y:y:y:x", "y:x:y:x:y:y:y:y",
                                    "y:x:y:y:x:x:x:x", "y:x:y:y:x:x:x:y",
                                    "y:x:y:y:x:x:y:x", "y:x:y:y:x:x:y:y",
                                    "y:x:y:y:x:y:x:x", "y:x:y:y:x:y:x:y",
                                    "y:x:y:y:x:y:y:x", "y:x:y:y:x:y:y:y",
                                    "y:x:y:y:y:x:x:x", "y:x:y:y:y:x:x:y",
                                    "y:x:y:y:y:x:y:x", "y:x:y:y:y:x:y:y",
                                    "y:x:y:y:y:y:x:x", "y:x:y:y:y:y:x:y",
                                    "y:x:y:y:y:y:y:x", "y:x:y:y:y:y:y:y",
                                    "y:y:x:x:x:x:x:x", "y:y:x:x:x:x:x:y",
                                    "y:y:x:x:x:x:y:x", "y:y:x:x:x:x:y:y",
                                    "y:y:x:x:x:y:x:x", "y:y:x:x:x:y:x:y",
                                    "y:y:x:x:x:y:y:x", "y:y:x:x:x:y:y:y",
                                    "y:y:x:x:y:x:x:x", "y:y:x:x:y:x:x:y",
                                    "y:y:x:x:y:x:y:x", "y:y:x:x:y:x:y:y",
                                    "y:y:x:x:y:y:x:x", "y:y:x:x:y:y:x:y",
                                    "y:y:x:x:y:y:y:x", "y:y:x:x:y:y:y:y",
                                    "y:y:x:y:x:x:x:x", "y:y:x:y:x:x:x:y",
                                    "y:y:x:y:x:x:y:x", "y:y:x:y:x:x:y:y",
                                    "y:y:x:y:x:y:x:x", "y:y:x:y:x:y:x:y",
                                    "y:y:x:y:x:y:y:x", "y:y:x:y:x:y:y:y",
                                    "y:y:x:y:y:x:x:x", "y:y:x:y:y:x:x:y",
                                    "y:y:x:y:y:x:y:x", "y:y:x:y:y:x:y:y",
                                    "y:y:x:y:y:y:x:x", "y:y:x:y:y:y:x:y",
                                    "y:y:x:y:y:y:y:x", "y:y:x:y:y:y:y:y",
                                    "y:y:y:x:x:x:x:x", "y:y:y:x:x:x:x:y",
                                    "y:y:y:x:x:x:y:x", "y:y:y:x:x:x:y:y",
                                    "y:y:y:x:x:y:x:x", "y:y:y:x:x:y:x:y",
                                    "y:y:y:x:x:y:y:x", "y:y:y:x:x:y:y:y",
                                    "y:y:y:x:y:x:x:x", "y:y:y:x:y:x:x:y",
                                    "y:y:y:x:y:x:y:x", "y:y:y:x:y:x:y:y",
                                    "y:y:y:x:y:y:x:x", "y:y:y:x:y:y:x:y",
                                    "y:y:y:x:y:y:y:x", "y:y:y:x:y:y:y:y",
                                    "y:y:y:y:x:x:x:x", "y:y:y:y:x:x:x:y",
                                    "y:y:y:y:x:x:y:x", "y:y:y:y:x:x:y:y",
                                    "y:y:y:y:x:y:x:x", "y:y:y:y:x:y:x:y",
                                    "y:y:y:y:x:y:y:x", "y:y:y:y:x:y:y:y",
                                    "y:y:y:y:y:x:x:x", "y:y:y:y:y:x:x:y",
                                    "y:y:y:y:y:x:y:x", "y:y:y:y:y:x:y:y",
                                    "y:y:y:y:y:y:x:x", "y:y:y:y:y:y:x:y",
                                    "y:y:y:y:y:y:y:x", "y:y:y:y:y:y:y:y",

                            ],
                            definitions : {
                                'x' : {
                                    validator : "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]",
                                    cardinality : 4,
                                    prevalidator : [
                                            {
                                                validator : "[0-9a-fA-F]",
                                                cardinality : 1
                                            },
                                            {
                                                validator : "[0-9a-fA-F][0-9a-fA-F]",
                                                cardinality : 2
                                            },
                                            {
                                                validator : "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]",
                                                cardinality : 3
                                            } ]
                                },
                                'y' : {
                                    validator : "[0]",
                                    cardinality : 1
                                },
                                'k' : {
                                    validator : "[1]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'z' : {
                                    validator : "[2]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'n' : {
                                    validator : "[0-8]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'm' : {
                                    validator : "[0-1]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'j' : {
                                    validator : "[0-9]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                }

                            }
                        },
                        "ipv6_subnet" : {
                            mask : [ "x:x:x:x:x:x:x:x/j[j]",
                                    "x:x:x:x:x:x:x:x/kmj",
                                    "x:x:x:x:x:x:x:x/kzn",
                                    "x:x:x:x:x:x:x:y/j[j]",
                                    "x:x:x:x:x:x:x:y/kmj",
                                    "x:x:x:x:x:x:x:y/kzn",
                                    "x:x:x:x:x:x:y:x/j[j]",
                                    "x:x:x:x:x:x:y:x/kmj",
                                    "x:x:x:x:x:x:y:x/kzn",
                                    "x:x:x:x:x:x:y:y/j[j]",
                                    "x:x:x:x:x:x:y:y/kmj",
                                    "x:x:x:x:x:x:y:y/kzn",
                                    "x:x:x:x:x:y:x:x/j[j]",
                                    "x:x:x:x:x:y:x:x/kmj",
                                    "x:x:x:x:x:y:x:x/kzn",
                                    "x:x:x:x:x:y:x:y/j[j]",
                                    "x:x:x:x:x:y:x:y/kmj",
                                    "x:x:x:x:x:y:x:y/kzn",
                                    "x:x:x:x:x:y:y:x/j[j]",
                                    "x:x:x:x:x:y:y:x/kmj",
                                    "x:x:x:x:x:y:y:x/kzn",
                                    "x:x:x:x:x:y:y:y/j[j]",
                                    "x:x:x:x:x:y:y:y/kmj",
                                    "x:x:x:x:x:y:y:y/kzn",
                                    "x:x:x:x:y:x:x:x/j[j]",
                                    "x:x:x:x:y:x:x:x/kmj",
                                    "x:x:x:x:y:x:x:x/kzn",
                                    "x:x:x:x:y:x:x:y/j[j]",
                                    "x:x:x:x:y:x:x:y/kmj",
                                    "x:x:x:x:y:x:x:y/kzn",
                                    "x:x:x:x:y:x:y:x/j[j]",
                                    "x:x:x:x:y:x:y:x/kmj",
                                    "x:x:x:x:y:x:y:x/kzn",
                                    "x:x:x:x:y:x:y:y/j[j]",
                                    "x:x:x:x:y:x:y:y/kmj",
                                    "x:x:x:x:y:x:y:y/kzn",
                                    "x:x:x:x:y:y:x:x/j[j]",
                                    "x:x:x:x:y:y:x:x/kmj",
                                    "x:x:x:x:y:y:x:x/kzn",
                                    "x:x:x:x:y:y:x:y/j[j]",
                                    "x:x:x:x:y:y:x:y/kmj",
                                    "x:x:x:x:y:y:x:y/kzn",
                                    "x:x:x:x:y:y:y:x/j[j]",
                                    "x:x:x:x:y:y:y:x/kmj",
                                    "x:x:x:x:y:y:y:x/kzn",
                                    "x:x:x:x:y:y:y:y/j[j]",
                                    "x:x:x:x:y:y:y:y/kmj",
                                    "x:x:x:x:y:y:y:y/kzn",
                                    "x:x:x:y:x:x:x:x/j[j]",
                                    "x:x:x:y:x:x:x:x/kmj",
                                    "x:x:x:y:x:x:x:x/kzn",
                                    "x:x:x:y:x:x:x:y/j[j]",
                                    "x:x:x:y:x:x:x:y/kmj",
                                    "x:x:x:y:x:x:x:y/kzn",
                                    "x:x:x:y:x:x:y:x/j[j]",
                                    "x:x:x:y:x:x:y:x/kmj",
                                    "x:x:x:y:x:x:y:x/kzn",
                                    "x:x:x:y:x:x:y:y/j[j]",
                                    "x:x:x:y:x:x:y:y/kmj",
                                    "x:x:x:y:x:x:y:y/kzn",
                                    "x:x:x:y:x:y:x:x/j[j]",
                                    "x:x:x:y:x:y:x:x/kmj",
                                    "x:x:x:y:x:y:x:x/kzn",
                                    "x:x:x:y:x:y:x:y/j[j]",
                                    "x:x:x:y:x:y:x:y/kmj",
                                    "x:x:x:y:x:y:x:y/kzn",
                                    "x:x:x:y:x:y:y:x/j[j]",
                                    "x:x:x:y:x:y:y:x/kmj",
                                    "x:x:x:y:x:y:y:x/kzn",
                                    "x:x:x:y:x:y:y:y/j[j]",
                                    "x:x:x:y:x:y:y:y/kmj",
                                    "x:x:x:y:x:y:y:y/kzn",
                                    "x:x:x:y:y:x:x:x/j[j]",
                                    "x:x:x:y:y:x:x:x/kmj",
                                    "x:x:x:y:y:x:x:x/kzn",
                                    "x:x:x:y:y:x:x:y/j[j]",
                                    "x:x:x:y:y:x:x:y/kmj",
                                    "x:x:x:y:y:x:x:y/kzn",
                                    "x:x:x:y:y:x:y:x/j[j]",
                                    "x:x:x:y:y:x:y:x/kmj",
                                    "x:x:x:y:y:x:y:x/kzn",
                                    "x:x:x:y:y:x:y:y/j[j]",
                                    "x:x:x:y:y:x:y:y/kmj",
                                    "x:x:x:y:y:x:y:y/kzn",
                                    "x:x:x:y:y:y:x:x/j[j]",
                                    "x:x:x:y:y:y:x:x/kmj",
                                    "x:x:x:y:y:y:x:x/kzn",
                                    "x:x:x:y:y:y:x:y/j[j]",
                                    "x:x:x:y:y:y:x:y/kmj",
                                    "x:x:x:y:y:y:x:y/kzn",
                                    "x:x:x:y:y:y:y:x/j[j]",
                                    "x:x:x:y:y:y:y:x/kmj",
                                    "x:x:x:y:y:y:y:x/kzn",
                                    "x:x:x:y:y:y:y:y/j[j]",
                                    "x:x:x:y:y:y:y:y/kmj",
                                    "x:x:x:y:y:y:y:y/kzn",
                                    "x:x:y:x:x:x:x:x/j[j]",
                                    "x:x:y:x:x:x:x:x/kmj",
                                    "x:x:y:x:x:x:x:x/kzn",
                                    "x:x:y:x:x:x:x:y/j[j]",
                                    "x:x:y:x:x:x:x:y/kmj",
                                    "x:x:y:x:x:x:x:y/kzn",
                                    "x:x:y:x:x:x:y:x/j[j]",
                                    "x:x:y:x:x:x:y:x/kmj",
                                    "x:x:y:x:x:x:y:x/kzn",
                                    "x:x:y:x:x:x:y:y/j[j]",
                                    "x:x:y:x:x:x:y:y/kmj",
                                    "x:x:y:x:x:x:y:y/kzn",
                                    "x:x:y:x:x:y:x:x/j[j]",
                                    "x:x:y:x:x:y:x:x/kmj",
                                    "x:x:y:x:x:y:x:x/kzn",
                                    "x:x:y:x:x:y:x:y/j[j]",
                                    "x:x:y:x:x:y:x:y/kmj",
                                    "x:x:y:x:x:y:x:y/kzn",
                                    "x:x:y:x:x:y:y:x/j[j]",
                                    "x:x:y:x:x:y:y:x/kmj",
                                    "x:x:y:x:x:y:y:x/kzn",
                                    "x:x:y:x:x:y:y:y/j[j]",
                                    "x:x:y:x:x:y:y:y/kmj",
                                    "x:x:y:x:x:y:y:y/kzn",
                                    "x:x:y:x:y:x:x:x/j[j]",
                                    "x:x:y:x:y:x:x:x/kmj",
                                    "x:x:y:x:y:x:x:x/kzn",
                                    "x:x:y:x:y:x:x:y/j[j]",
                                    "x:x:y:x:y:x:x:y/kmj",
                                    "x:x:y:x:y:x:x:y/kzn",
                                    "x:x:y:x:y:x:y:x/j[j]",
                                    "x:x:y:x:y:x:y:x/kmj",
                                    "x:x:y:x:y:x:y:x/kzn",
                                    "x:x:y:x:y:x:y:y/j[j]",
                                    "x:x:y:x:y:x:y:y/kmj",
                                    "x:x:y:x:y:x:y:y/kzn",
                                    "x:x:y:x:y:y:x:x/j[j]",
                                    "x:x:y:x:y:y:x:x/kmj",
                                    "x:x:y:x:y:y:x:x/kzn",
                                    "x:x:y:x:y:y:x:y/j[j]",
                                    "x:x:y:x:y:y:x:y/kmj",
                                    "x:x:y:x:y:y:x:y/kzn",
                                    "x:x:y:x:y:y:y:x/j[j]",
                                    "x:x:y:x:y:y:y:x/kmj",
                                    "x:x:y:x:y:y:y:x/kzn",
                                    "x:x:y:x:y:y:y:y/j[j]",
                                    "x:x:y:x:y:y:y:y/kmj",
                                    "x:x:y:x:y:y:y:y/kzn",
                                    "x:x:y:y:x:x:x:x/j[j]",
                                    "x:x:y:y:x:x:x:x/kmj",
                                    "x:x:y:y:x:x:x:x/kzn",
                                    "x:x:y:y:x:x:x:y/j[j]",
                                    "x:x:y:y:x:x:x:y/kmj",
                                    "x:x:y:y:x:x:x:y/kzn",
                                    "x:x:y:y:x:x:y:x/j[j]",
                                    "x:x:y:y:x:x:y:x/kmj",
                                    "x:x:y:y:x:x:y:x/kzn",
                                    "x:x:y:y:x:x:y:y/j[j]",
                                    "x:x:y:y:x:x:y:y/kmj",
                                    "x:x:y:y:x:x:y:y/kzn",
                                    "x:x:y:y:x:y:x:x/j[j]",
                                    "x:x:y:y:x:y:x:x/kmj",
                                    "x:x:y:y:x:y:x:x/kzn",
                                    "x:x:y:y:x:y:x:y/j[j]",
                                    "x:x:y:y:x:y:x:y/kmj",
                                    "x:x:y:y:x:y:x:y/kzn",
                                    "x:x:y:y:x:y:y:x/j[j]",
                                    "x:x:y:y:x:y:y:x/kmj",
                                    "x:x:y:y:x:y:y:x/kzn",
                                    "x:x:y:y:x:y:y:y/j[j]",
                                    "x:x:y:y:x:y:y:y/kmj",
                                    "x:x:y:y:x:y:y:y/kzn",
                                    "x:x:y:y:y:x:x:x/j[j]",
                                    "x:x:y:y:y:x:x:x/kmj",
                                    "x:x:y:y:y:x:x:x/kzn",
                                    "x:x:y:y:y:x:x:y/j[j]",
                                    "x:x:y:y:y:x:x:y/kmj",
                                    "x:x:y:y:y:x:x:y/kzn",
                                    "x:x:y:y:y:x:y:x/j[j]",
                                    "x:x:y:y:y:x:y:x/kmj",
                                    "x:x:y:y:y:x:y:x/kzn",
                                    "x:x:y:y:y:x:y:y/j[j]",
                                    "x:x:y:y:y:x:y:y/kmj",
                                    "x:x:y:y:y:x:y:y/kzn",
                                    "x:x:y:y:y:y:x:x/j[j]",
                                    "x:x:y:y:y:y:x:x/kmj",
                                    "x:x:y:y:y:y:x:x/kzn",
                                    "x:x:y:y:y:y:x:y/j[j]",
                                    "x:x:y:y:y:y:x:y/kmj",
                                    "x:x:y:y:y:y:x:y/kzn",
                                    "x:x:y:y:y:y:y:x/j[j]",
                                    "x:x:y:y:y:y:y:x/kmj",
                                    "x:x:y:y:y:y:y:x/kzn",
                                    "x:x:y:y:y:y:y:y/j[j]",
                                    "x:x:y:y:y:y:y:y/kmj",
                                    "x:x:y:y:y:y:y:y/kzn",
                                    "x:y:x:x:x:x:x:x/j[j]",
                                    "x:y:x:x:x:x:x:x/kmj",
                                    "x:y:x:x:x:x:x:x/kzn",
                                    "x:y:x:x:x:x:x:y/j[j]",
                                    "x:y:x:x:x:x:x:y/kmj",
                                    "x:y:x:x:x:x:x:y/kzn",
                                    "x:y:x:x:x:x:y:x/j[j]",
                                    "x:y:x:x:x:x:y:x/kmj",
                                    "x:y:x:x:x:x:y:x/kzn",
                                    "x:y:x:x:x:x:y:y/j[j]",
                                    "x:y:x:x:x:x:y:y/kmj",
                                    "x:y:x:x:x:x:y:y/kzn",
                                    "x:y:x:x:x:y:x:x/j[j]",
                                    "x:y:x:x:x:y:x:x/kmj",
                                    "x:y:x:x:x:y:x:x/kzn",
                                    "x:y:x:x:x:y:x:y/j[j]",
                                    "x:y:x:x:x:y:x:y/kmj",
                                    "x:y:x:x:x:y:x:y/kzn",
                                    "x:y:x:x:x:y:y:x/j[j]",
                                    "x:y:x:x:x:y:y:x/kmj",
                                    "x:y:x:x:x:y:y:x/kzn",
                                    "x:y:x:x:x:y:y:y/j[j]",
                                    "x:y:x:x:x:y:y:y/kmj",
                                    "x:y:x:x:x:y:y:y/kzn",
                                    "x:y:x:x:y:x:x:x/j[j]",
                                    "x:y:x:x:y:x:x:x/kmj",
                                    "x:y:x:x:y:x:x:x/kzn",
                                    "x:y:x:x:y:x:x:y/j[j]",
                                    "x:y:x:x:y:x:x:y/kmj",
                                    "x:y:x:x:y:x:x:y/kzn",
                                    "x:y:x:x:y:x:y:x/j[j]",
                                    "x:y:x:x:y:x:y:x/kmj",
                                    "x:y:x:x:y:x:y:x/kzn",
                                    "x:y:x:x:y:x:y:y/j[j]",
                                    "x:y:x:x:y:x:y:y/kmj",
                                    "x:y:x:x:y:x:y:y/kzn",
                                    "x:y:x:x:y:y:x:x/j[j]",
                                    "x:y:x:x:y:y:x:x/kmj",
                                    "x:y:x:x:y:y:x:x/kzn",
                                    "x:y:x:x:y:y:x:y/j[j]",
                                    "x:y:x:x:y:y:x:y/kmj",
                                    "x:y:x:x:y:y:x:y/kzn",
                                    "x:y:x:x:y:y:y:x/j[j]",
                                    "x:y:x:x:y:y:y:x/kmj",
                                    "x:y:x:x:y:y:y:x/kzn",
                                    "x:y:x:x:y:y:y:y/j[j]",
                                    "x:y:x:x:y:y:y:y/kmj",
                                    "x:y:x:x:y:y:y:y/kzn",
                                    "x:y:x:y:x:x:x:x/j[j]",
                                    "x:y:x:y:x:x:x:x/kmj",
                                    "x:y:x:y:x:x:x:x/kzn",
                                    "x:y:x:y:x:x:x:y/j[j]",
                                    "x:y:x:y:x:x:x:y/kmj",
                                    "x:y:x:y:x:x:x:y/kzn",
                                    "x:y:x:y:x:x:y:x/j[j]",
                                    "x:y:x:y:x:x:y:x/kmj",
                                    "x:y:x:y:x:x:y:x/kzn",
                                    "x:y:x:y:x:x:y:y/j[j]",
                                    "x:y:x:y:x:x:y:y/kmj",
                                    "x:y:x:y:x:x:y:y/kzn",
                                    "x:y:x:y:x:y:x:x/j[j]",
                                    "x:y:x:y:x:y:x:x/kmj",
                                    "x:y:x:y:x:y:x:x/kzn",
                                    "x:y:x:y:x:y:x:y/j[j]",
                                    "x:y:x:y:x:y:x:y/kmj",
                                    "x:y:x:y:x:y:x:y/kzn",
                                    "x:y:x:y:x:y:y:x/j[j]",
                                    "x:y:x:y:x:y:y:x/kmj",
                                    "x:y:x:y:x:y:y:x/kzn",
                                    "x:y:x:y:x:y:y:y/j[j]",
                                    "x:y:x:y:x:y:y:y/kmj",
                                    "x:y:x:y:x:y:y:y/kzn",
                                    "x:y:x:y:y:x:x:x/j[j]",
                                    "x:y:x:y:y:x:x:x/kmj",
                                    "x:y:x:y:y:x:x:x/kzn",
                                    "x:y:x:y:y:x:x:y/j[j]",
                                    "x:y:x:y:y:x:x:y/kmj",
                                    "x:y:x:y:y:x:x:y/kzn",
                                    "x:y:x:y:y:x:y:x/j[j]",
                                    "x:y:x:y:y:x:y:x/kmj",
                                    "x:y:x:y:y:x:y:x/kzn",
                                    "x:y:x:y:y:x:y:y/j[j]",
                                    "x:y:x:y:y:x:y:y/kmj",
                                    "x:y:x:y:y:x:y:y/kzn",
                                    "x:y:x:y:y:y:x:x/j[j]",
                                    "x:y:x:y:y:y:x:x/kmj",
                                    "x:y:x:y:y:y:x:x/kzn",
                                    "x:y:x:y:y:y:x:y/j[j]",
                                    "x:y:x:y:y:y:x:y/kmj",
                                    "x:y:x:y:y:y:x:y/kzn",
                                    "x:y:x:y:y:y:y:x/j[j]",
                                    "x:y:x:y:y:y:y:x/kmj",
                                    "x:y:x:y:y:y:y:x/kzn",
                                    "x:y:x:y:y:y:y:y/j[j]",
                                    "x:y:x:y:y:y:y:y/kmj",
                                    "x:y:x:y:y:y:y:y/kzn",
                                    "x:y:y:x:x:x:x:x/j[j]",
                                    "x:y:y:x:x:x:x:x/kmj",
                                    "x:y:y:x:x:x:x:x/kzn",
                                    "x:y:y:x:x:x:x:y/j[j]",
                                    "x:y:y:x:x:x:x:y/kmj",
                                    "x:y:y:x:x:x:x:y/kzn",
                                    "x:y:y:x:x:x:y:x/j[j]",
                                    "x:y:y:x:x:x:y:x/kmj",
                                    "x:y:y:x:x:x:y:x/kzn",
                                    "x:y:y:x:x:x:y:y/j[j]",
                                    "x:y:y:x:x:x:y:y/kmj",
                                    "x:y:y:x:x:x:y:y/kzn",
                                    "x:y:y:x:x:y:x:x/j[j]",
                                    "x:y:y:x:x:y:x:x/kmj",
                                    "x:y:y:x:x:y:x:x/kzn",
                                    "x:y:y:x:x:y:x:y/j[j]",
                                    "x:y:y:x:x:y:x:y/kmj",
                                    "x:y:y:x:x:y:x:y/kzn",
                                    "x:y:y:x:x:y:y:x/j[j]",
                                    "x:y:y:x:x:y:y:x/kmj",
                                    "x:y:y:x:x:y:y:x/kzn",
                                    "x:y:y:x:x:y:y:y/j[j]",
                                    "x:y:y:x:x:y:y:y/kmj",
                                    "x:y:y:x:x:y:y:y/kzn",
                                    "x:y:y:x:y:x:x:x/j[j]",
                                    "x:y:y:x:y:x:x:x/kmj",
                                    "x:y:y:x:y:x:x:x/kzn",
                                    "x:y:y:x:y:x:x:y/j[j]",
                                    "x:y:y:x:y:x:x:y/kmj",
                                    "x:y:y:x:y:x:x:y/kzn",
                                    "x:y:y:x:y:x:y:x/j[j]",
                                    "x:y:y:x:y:x:y:x/kmj",
                                    "x:y:y:x:y:x:y:x/kzn",
                                    "x:y:y:x:y:x:y:y/j[j]",
                                    "x:y:y:x:y:x:y:y/kmj",
                                    "x:y:y:x:y:x:y:y/kzn",
                                    "x:y:y:x:y:y:x:x/j[j]",
                                    "x:y:y:x:y:y:x:x/kmj",
                                    "x:y:y:x:y:y:x:x/kzn",
                                    "x:y:y:x:y:y:x:y/j[j]",
                                    "x:y:y:x:y:y:x:y/kmj",
                                    "x:y:y:x:y:y:x:y/kzn",
                                    "x:y:y:x:y:y:y:x/j[j]",
                                    "x:y:y:x:y:y:y:x/kmj",
                                    "x:y:y:x:y:y:y:x/kzn",
                                    "x:y:y:x:y:y:y:y/j[j]",
                                    "x:y:y:x:y:y:y:y/kmj",
                                    "x:y:y:x:y:y:y:y/kzn",
                                    "x:y:y:y:x:x:x:x/j[j]",
                                    "x:y:y:y:x:x:x:x/kmj",
                                    "x:y:y:y:x:x:x:x/kzn",
                                    "x:y:y:y:x:x:x:y/j[j]",
                                    "x:y:y:y:x:x:x:y/kmj",
                                    "x:y:y:y:x:x:x:y/kzn",
                                    "x:y:y:y:x:x:y:x/j[j]",
                                    "x:y:y:y:x:x:y:x/kmj",
                                    "x:y:y:y:x:x:y:x/kzn",
                                    "x:y:y:y:x:x:y:y/j[j]",
                                    "x:y:y:y:x:x:y:y/kmj",
                                    "x:y:y:y:x:x:y:y/kzn",
                                    "x:y:y:y:x:y:x:x/j[j]",
                                    "x:y:y:y:x:y:x:x/kmj",
                                    "x:y:y:y:x:y:x:x/kzn",
                                    "x:y:y:y:x:y:x:y/j[j]",
                                    "x:y:y:y:x:y:x:y/kmj",
                                    "x:y:y:y:x:y:x:y/kzn",
                                    "x:y:y:y:x:y:y:x/j[j]",
                                    "x:y:y:y:x:y:y:x/kmj",
                                    "x:y:y:y:x:y:y:x/kzn",
                                    "x:y:y:y:x:y:y:y/j[j]",
                                    "x:y:y:y:x:y:y:y/kmj",
                                    "x:y:y:y:x:y:y:y/kzn",
                                    "x:y:y:y:y:x:x:x/j[j]",
                                    "x:y:y:y:y:x:x:x/kmj",
                                    "x:y:y:y:y:x:x:x/kzn",
                                    "x:y:y:y:y:x:x:y/j[j]",
                                    "x:y:y:y:y:x:x:y/kmj",
                                    "x:y:y:y:y:x:x:y/kzn",
                                    "x:y:y:y:y:x:y:x/j[j]",
                                    "x:y:y:y:y:x:y:x/kmj",
                                    "x:y:y:y:y:x:y:x/kzn",
                                    "x:y:y:y:y:x:y:y/j[j]",
                                    "x:y:y:y:y:x:y:y/kmj",
                                    "x:y:y:y:y:x:y:y/kzn",
                                    "x:y:y:y:y:y:x:x/j[j]",
                                    "x:y:y:y:y:y:x:x/kmj",
                                    "x:y:y:y:y:y:x:x/kzn",
                                    "x:y:y:y:y:y:x:y/j[j]",
                                    "x:y:y:y:y:y:x:y/kmj",
                                    "x:y:y:y:y:y:x:y/kzn",
                                    "x:y:y:y:y:y:y:x/j[j]",
                                    "x:y:y:y:y:y:y:x/kmj",
                                    "x:y:y:y:y:y:y:x/kzn",
                                    "x:y:y:y:y:y:y:y/j[j]",
                                    "x:y:y:y:y:y:y:y/kmj",
                                    "x:y:y:y:y:y:y:y/kzn",
                                    "y:x:x:x:x:x:x:x/j[j]",
                                    "y:x:x:x:x:x:x:x/kmj",
                                    "y:x:x:x:x:x:x:x/kzn",
                                    "y:x:x:x:x:x:x:y/j[j]",
                                    "y:x:x:x:x:x:x:y/kmj",
                                    "y:x:x:x:x:x:x:y/kzn",
                                    "y:x:x:x:x:x:y:x/j[j]",
                                    "y:x:x:x:x:x:y:x/kmj",
                                    "y:x:x:x:x:x:y:x/kzn",
                                    "y:x:x:x:x:x:y:y/j[j]",
                                    "y:x:x:x:x:x:y:y/kmj",
                                    "y:x:x:x:x:x:y:y/kzn",
                                    "y:x:x:x:x:y:x:x/j[j]",
                                    "y:x:x:x:x:y:x:x/kmj",
                                    "y:x:x:x:x:y:x:x/kzn",
                                    "y:x:x:x:x:y:x:y/j[j]",
                                    "y:x:x:x:x:y:x:y/kmj",
                                    "y:x:x:x:x:y:x:y/kzn",
                                    "y:x:x:x:x:y:y:x/j[j]",
                                    "y:x:x:x:x:y:y:x/kmj",
                                    "y:x:x:x:x:y:y:x/kzn",
                                    "y:x:x:x:x:y:y:y/j[j]",
                                    "y:x:x:x:x:y:y:y/kmj",
                                    "y:x:x:x:x:y:y:y/kzn",
                                    "y:x:x:x:y:x:x:x/j[j]",
                                    "y:x:x:x:y:x:x:x/kmj",
                                    "y:x:x:x:y:x:x:x/kzn",
                                    "y:x:x:x:y:x:x:y/j[j]",
                                    "y:x:x:x:y:x:x:y/kmj",
                                    "y:x:x:x:y:x:x:y/kzn",
                                    "y:x:x:x:y:x:y:x/j[j]",
                                    "y:x:x:x:y:x:y:x/kmj",
                                    "y:x:x:x:y:x:y:x/kzn",
                                    "y:x:x:x:y:x:y:y/j[j]",
                                    "y:x:x:x:y:x:y:y/kmj",
                                    "y:x:x:x:y:x:y:y/kzn",
                                    "y:x:x:x:y:y:x:x/j[j]",
                                    "y:x:x:x:y:y:x:x/kmj",
                                    "y:x:x:x:y:y:x:x/kzn",
                                    "y:x:x:x:y:y:x:y/j[j]",
                                    "y:x:x:x:y:y:x:y/kmj",
                                    "y:x:x:x:y:y:x:y/kzn",
                                    "y:x:x:x:y:y:y:x/j[j]",
                                    "y:x:x:x:y:y:y:x/kmj",
                                    "y:x:x:x:y:y:y:x/kzn",
                                    "y:x:x:x:y:y:y:y/j[j]",
                                    "y:x:x:x:y:y:y:y/kmj",
                                    "y:x:x:x:y:y:y:y/kzn",
                                    "y:x:x:y:x:x:x:x/j[j]",
                                    "y:x:x:y:x:x:x:x/kmj",
                                    "y:x:x:y:x:x:x:x/kzn",
                                    "y:x:x:y:x:x:x:y/j[j]",
                                    "y:x:x:y:x:x:x:y/kmj",
                                    "y:x:x:y:x:x:x:y/kzn",
                                    "y:x:x:y:x:x:y:x/j[j]",
                                    "y:x:x:y:x:x:y:x/kmj",
                                    "y:x:x:y:x:x:y:x/kzn",
                                    "y:x:x:y:x:x:y:y/j[j]",
                                    "y:x:x:y:x:x:y:y/kmj",
                                    "y:x:x:y:x:x:y:y/kzn",
                                    "y:x:x:y:x:y:x:x/j[j]",
                                    "y:x:x:y:x:y:x:x/kmj",
                                    "y:x:x:y:x:y:x:x/kzn",
                                    "y:x:x:y:x:y:x:y/j[j]",
                                    "y:x:x:y:x:y:x:y/kmj",
                                    "y:x:x:y:x:y:x:y/kzn",
                                    "y:x:x:y:x:y:y:x/j[j]",
                                    "y:x:x:y:x:y:y:x/kmj",
                                    "y:x:x:y:x:y:y:x/kzn",
                                    "y:x:x:y:x:y:y:y/j[j]",
                                    "y:x:x:y:x:y:y:y/kmj",
                                    "y:x:x:y:x:y:y:y/kzn",
                                    "y:x:x:y:y:x:x:x/j[j]",
                                    "y:x:x:y:y:x:x:x/kmj",
                                    "y:x:x:y:y:x:x:x/kzn",
                                    "y:x:x:y:y:x:x:y/j[j]",
                                    "y:x:x:y:y:x:x:y/kmj",
                                    "y:x:x:y:y:x:x:y/kzn",
                                    "y:x:x:y:y:x:y:x/j[j]",
                                    "y:x:x:y:y:x:y:x/kmj",
                                    "y:x:x:y:y:x:y:x/kzn",
                                    "y:x:x:y:y:x:y:y/j[j]",
                                    "y:x:x:y:y:x:y:y/kmj",
                                    "y:x:x:y:y:x:y:y/kzn",
                                    "y:x:x:y:y:y:x:x/j[j]",
                                    "y:x:x:y:y:y:x:x/kmj",
                                    "y:x:x:y:y:y:x:x/kzn",
                                    "y:x:x:y:y:y:x:y/j[j]",
                                    "y:x:x:y:y:y:x:y/kmj",
                                    "y:x:x:y:y:y:x:y/kzn",
                                    "y:x:x:y:y:y:y:x/j[j]",
                                    "y:x:x:y:y:y:y:x/kmj",
                                    "y:x:x:y:y:y:y:x/kzn",
                                    "y:x:x:y:y:y:y:y/j[j]",
                                    "y:x:x:y:y:y:y:y/kmj",
                                    "y:x:x:y:y:y:y:y/kzn",
                                    "y:x:y:x:x:x:x:x/j[j]",
                                    "y:x:y:x:x:x:x:x/kmj",
                                    "y:x:y:x:x:x:x:x/kzn",
                                    "y:x:y:x:x:x:x:y/j[j]",
                                    "y:x:y:x:x:x:x:y/kmj",
                                    "y:x:y:x:x:x:x:y/kzn",
                                    "y:x:y:x:x:x:y:x/j[j]",
                                    "y:x:y:x:x:x:y:x/kmj",
                                    "y:x:y:x:x:x:y:x/kzn",
                                    "y:x:y:x:x:x:y:y/j[j]",
                                    "y:x:y:x:x:x:y:y/kmj",
                                    "y:x:y:x:x:x:y:y/kzn",
                                    "y:x:y:x:x:y:x:x/j[j]",
                                    "y:x:y:x:x:y:x:x/kmj",
                                    "y:x:y:x:x:y:x:x/kzn",
                                    "y:x:y:x:x:y:x:y/j[j]",
                                    "y:x:y:x:x:y:x:y/kmj",
                                    "y:x:y:x:x:y:x:y/kzn",
                                    "y:x:y:x:x:y:y:x/j[j]",
                                    "y:x:y:x:x:y:y:x/kmj",
                                    "y:x:y:x:x:y:y:x/kzn",
                                    "y:x:y:x:x:y:y:y/j[j]",
                                    "y:x:y:x:x:y:y:y/kmj",
                                    "y:x:y:x:x:y:y:y/kzn",
                                    "y:x:y:x:y:x:x:x/j[j]",
                                    "y:x:y:x:y:x:x:x/kmj",
                                    "y:x:y:x:y:x:x:x/kzn",
                                    "y:x:y:x:y:x:x:y/j[j]",
                                    "y:x:y:x:y:x:x:y/kmj",
                                    "y:x:y:x:y:x:x:y/kzn",
                                    "y:x:y:x:y:x:y:x/j[j]",
                                    "y:x:y:x:y:x:y:x/kmj",
                                    "y:x:y:x:y:x:y:x/kzn",
                                    "y:x:y:x:y:x:y:y/j[j]",
                                    "y:x:y:x:y:x:y:y/kmj",
                                    "y:x:y:x:y:x:y:y/kzn",
                                    "y:x:y:x:y:y:x:x/j[j]",
                                    "y:x:y:x:y:y:x:x/kmj",
                                    "y:x:y:x:y:y:x:x/kzn",
                                    "y:x:y:x:y:y:x:y/j[j]",
                                    "y:x:y:x:y:y:x:y/kmj",
                                    "y:x:y:x:y:y:x:y/kzn",
                                    "y:x:y:x:y:y:y:x/j[j]",
                                    "y:x:y:x:y:y:y:x/kmj",
                                    "y:x:y:x:y:y:y:x/kzn",
                                    "y:x:y:x:y:y:y:y/j[j]",
                                    "y:x:y:x:y:y:y:y/kmj",
                                    "y:x:y:x:y:y:y:y/kzn",
                                    "y:x:y:y:x:x:x:x/j[j]",
                                    "y:x:y:y:x:x:x:x/kmj",
                                    "y:x:y:y:x:x:x:x/kzn",
                                    "y:x:y:y:x:x:x:y/j[j]",
                                    "y:x:y:y:x:x:x:y/kmj",
                                    "y:x:y:y:x:x:x:y/kzn",
                                    "y:x:y:y:x:x:y:x/j[j]",
                                    "y:x:y:y:x:x:y:x/kmj",
                                    "y:x:y:y:x:x:y:x/kzn",
                                    "y:x:y:y:x:x:y:y/j[j]",
                                    "y:x:y:y:x:x:y:y/kmj",
                                    "y:x:y:y:x:x:y:y/kzn",
                                    "y:x:y:y:x:y:x:x/j[j]",
                                    "y:x:y:y:x:y:x:x/kmj",
                                    "y:x:y:y:x:y:x:x/kzn",
                                    "y:x:y:y:x:y:x:y/j[j]",
                                    "y:x:y:y:x:y:x:y/kmj",
                                    "y:x:y:y:x:y:x:y/kzn",
                                    "y:x:y:y:x:y:y:x/j[j]",
                                    "y:x:y:y:x:y:y:x/kmj",
                                    "y:x:y:y:x:y:y:x/kzn",
                                    "y:x:y:y:x:y:y:y/j[j]",
                                    "y:x:y:y:x:y:y:y/kmj",
                                    "y:x:y:y:x:y:y:y/kzn",
                                    "y:x:y:y:y:x:x:x/j[j]",
                                    "y:x:y:y:y:x:x:x/kmj",
                                    "y:x:y:y:y:x:x:x/kzn",
                                    "y:x:y:y:y:x:x:y/j[j]",
                                    "y:x:y:y:y:x:x:y/kmj",
                                    "y:x:y:y:y:x:x:y/kzn",
                                    "y:x:y:y:y:x:y:x/j[j]",
                                    "y:x:y:y:y:x:y:x/kmj",
                                    "y:x:y:y:y:x:y:x/kzn",
                                    "y:x:y:y:y:x:y:y/j[j]",
                                    "y:x:y:y:y:x:y:y/kmj",
                                    "y:x:y:y:y:x:y:y/kzn",
                                    "y:x:y:y:y:y:x:x/j[j]",
                                    "y:x:y:y:y:y:x:x/kmj",
                                    "y:x:y:y:y:y:x:x/kzn",
                                    "y:x:y:y:y:y:x:y/j[j]",
                                    "y:x:y:y:y:y:x:y/kmj",
                                    "y:x:y:y:y:y:x:y/kzn",
                                    "y:x:y:y:y:y:y:x/j[j]",
                                    "y:x:y:y:y:y:y:x/kmj",
                                    "y:x:y:y:y:y:y:x/kzn",
                                    "y:x:y:y:y:y:y:y/j[j]",
                                    "y:x:y:y:y:y:y:y/kmj",
                                    "y:x:y:y:y:y:y:y/kzn",
                                    "y:y:x:x:x:x:x:x/j[j]",
                                    "y:y:x:x:x:x:x:x/kmj",
                                    "y:y:x:x:x:x:x:x/kzn",
                                    "y:y:x:x:x:x:x:y/j[j]",
                                    "y:y:x:x:x:x:x:y/kmj",
                                    "y:y:x:x:x:x:x:y/kzn",
                                    "y:y:x:x:x:x:y:x/j[j]",
                                    "y:y:x:x:x:x:y:x/kmj",
                                    "y:y:x:x:x:x:y:x/kzn",
                                    "y:y:x:x:x:x:y:y/j[j]",
                                    "y:y:x:x:x:x:y:y/kmj",
                                    "y:y:x:x:x:x:y:y/kzn",
                                    "y:y:x:x:x:y:x:x/j[j]",
                                    "y:y:x:x:x:y:x:x/kmj",
                                    "y:y:x:x:x:y:x:x/kzn",
                                    "y:y:x:x:x:y:x:y/j[j]",
                                    "y:y:x:x:x:y:x:y/kmj",
                                    "y:y:x:x:x:y:x:y/kzn",
                                    "y:y:x:x:x:y:y:x/j[j]",
                                    "y:y:x:x:x:y:y:x/kmj",
                                    "y:y:x:x:x:y:y:x/kzn",
                                    "y:y:x:x:x:y:y:y/j[j]",
                                    "y:y:x:x:x:y:y:y/kmj",
                                    "y:y:x:x:x:y:y:y/kzn",
                                    "y:y:x:x:y:x:x:x/j[j]",
                                    "y:y:x:x:y:x:x:x/kmj",
                                    "y:y:x:x:y:x:x:x/kzn",
                                    "y:y:x:x:y:x:x:y/j[j]",
                                    "y:y:x:x:y:x:x:y/kmj",
                                    "y:y:x:x:y:x:x:y/kzn",
                                    "y:y:x:x:y:x:y:x/j[j]",
                                    "y:y:x:x:y:x:y:x/kmj",
                                    "y:y:x:x:y:x:y:x/kzn",
                                    "y:y:x:x:y:x:y:y/j[j]",
                                    "y:y:x:x:y:x:y:y/kmj",
                                    "y:y:x:x:y:x:y:y/kzn",
                                    "y:y:x:x:y:y:x:x/j[j]",
                                    "y:y:x:x:y:y:x:x/kmj",
                                    "y:y:x:x:y:y:x:x/kzn",
                                    "y:y:x:x:y:y:x:y/j[j]",
                                    "y:y:x:x:y:y:x:y/kmj",
                                    "y:y:x:x:y:y:x:y/kzn",
                                    "y:y:x:x:y:y:y:x/j[j]",
                                    "y:y:x:x:y:y:y:x/kmj",
                                    "y:y:x:x:y:y:y:x/kzn",
                                    "y:y:x:x:y:y:y:y/j[j]",
                                    "y:y:x:x:y:y:y:y/kmj",
                                    "y:y:x:x:y:y:y:y/kzn",
                                    "y:y:x:y:x:x:x:x/j[j]",
                                    "y:y:x:y:x:x:x:x/kmj",
                                    "y:y:x:y:x:x:x:x/kzn",
                                    "y:y:x:y:x:x:x:y/j[j]",
                                    "y:y:x:y:x:x:x:y/kmj",
                                    "y:y:x:y:x:x:x:y/kzn",
                                    "y:y:x:y:x:x:y:x/j[j]",
                                    "y:y:x:y:x:x:y:x/kmj",
                                    "y:y:x:y:x:x:y:x/kzn",
                                    "y:y:x:y:x:x:y:y/j[j]",
                                    "y:y:x:y:x:x:y:y/kmj",
                                    "y:y:x:y:x:x:y:y/kzn",
                                    "y:y:x:y:x:y:x:x/j[j]",
                                    "y:y:x:y:x:y:x:x/kmj",
                                    "y:y:x:y:x:y:x:x/kzn",
                                    "y:y:x:y:x:y:x:y/j[j]",
                                    "y:y:x:y:x:y:x:y/kmj",
                                    "y:y:x:y:x:y:x:y/kzn",
                                    "y:y:x:y:x:y:y:x/j[j]",
                                    "y:y:x:y:x:y:y:x/kmj",
                                    "y:y:x:y:x:y:y:x/kzn",
                                    "y:y:x:y:x:y:y:y/j[j]",
                                    "y:y:x:y:x:y:y:y/kmj",
                                    "y:y:x:y:x:y:y:y/kzn",
                                    "y:y:x:y:y:x:x:x/j[j]",
                                    "y:y:x:y:y:x:x:x/kmj",
                                    "y:y:x:y:y:x:x:x/kzn",
                                    "y:y:x:y:y:x:x:y/j[j]",
                                    "y:y:x:y:y:x:x:y/kmj",
                                    "y:y:x:y:y:x:x:y/kzn",
                                    "y:y:x:y:y:x:y:x/j[j]",
                                    "y:y:x:y:y:x:y:x/kmj",
                                    "y:y:x:y:y:x:y:x/kzn",
                                    "y:y:x:y:y:x:y:y/j[j]",
                                    "y:y:x:y:y:x:y:y/kmj",
                                    "y:y:x:y:y:x:y:y/kzn",
                                    "y:y:x:y:y:y:x:x/j[j]",
                                    "y:y:x:y:y:y:x:x/kmj",
                                    "y:y:x:y:y:y:x:x/kzn",
                                    "y:y:x:y:y:y:x:y/j[j]",
                                    "y:y:x:y:y:y:x:y/kmj",
                                    "y:y:x:y:y:y:x:y/kzn",
                                    "y:y:x:y:y:y:y:x/j[j]",
                                    "y:y:x:y:y:y:y:x/kmj",
                                    "y:y:x:y:y:y:y:x/kzn",
                                    "y:y:x:y:y:y:y:y/j[j]",
                                    "y:y:x:y:y:y:y:y/kmj",
                                    "y:y:x:y:y:y:y:y/kzn",
                                    "y:y:y:x:x:x:x:x/j[j]",
                                    "y:y:y:x:x:x:x:x/kmj",
                                    "y:y:y:x:x:x:x:x/kzn",
                                    "y:y:y:x:x:x:x:y/j[j]",
                                    "y:y:y:x:x:x:x:y/kmj",
                                    "y:y:y:x:x:x:x:y/kzn",
                                    "y:y:y:x:x:x:y:x/j[j]",
                                    "y:y:y:x:x:x:y:x/kmj",
                                    "y:y:y:x:x:x:y:x/kzn",
                                    "y:y:y:x:x:x:y:y/j[j]",
                                    "y:y:y:x:x:x:y:y/kmj",
                                    "y:y:y:x:x:x:y:y/kzn",
                                    "y:y:y:x:x:y:x:x/j[j]",
                                    "y:y:y:x:x:y:x:x/kmj",
                                    "y:y:y:x:x:y:x:x/kzn",
                                    "y:y:y:x:x:y:x:y/j[j]",
                                    "y:y:y:x:x:y:x:y/kmj",
                                    "y:y:y:x:x:y:x:y/kzn",
                                    "y:y:y:x:x:y:y:x/j[j]",
                                    "y:y:y:x:x:y:y:x/kmj",
                                    "y:y:y:x:x:y:y:x/kzn",
                                    "y:y:y:x:x:y:y:y/j[j]",
                                    "y:y:y:x:x:y:y:y/kmj",
                                    "y:y:y:x:x:y:y:y/kzn",
                                    "y:y:y:x:y:x:x:x/j[j]",
                                    "y:y:y:x:y:x:x:x/kmj",
                                    "y:y:y:x:y:x:x:x/kzn",
                                    "y:y:y:x:y:x:x:y/j[j]",
                                    "y:y:y:x:y:x:x:y/kmj",
                                    "y:y:y:x:y:x:x:y/kzn",
                                    "y:y:y:x:y:x:y:x/j[j]",
                                    "y:y:y:x:y:x:y:x/kmj",
                                    "y:y:y:x:y:x:y:x/kzn",
                                    "y:y:y:x:y:x:y:y/j[j]",
                                    "y:y:y:x:y:x:y:y/kmj",
                                    "y:y:y:x:y:x:y:y/kzn",
                                    "y:y:y:x:y:y:x:x/j[j]",
                                    "y:y:y:x:y:y:x:x/kmj",
                                    "y:y:y:x:y:y:x:x/kzn",
                                    "y:y:y:x:y:y:x:y/j[j]",
                                    "y:y:y:x:y:y:x:y/kmj",
                                    "y:y:y:x:y:y:x:y/kzn",
                                    "y:y:y:x:y:y:y:x/j[j]",
                                    "y:y:y:x:y:y:y:x/kmj",
                                    "y:y:y:x:y:y:y:x/kzn",
                                    "y:y:y:x:y:y:y:y/j[j]",
                                    "y:y:y:x:y:y:y:y/kmj",
                                    "y:y:y:x:y:y:y:y/kzn",
                                    "y:y:y:y:x:x:x:x/j[j]",
                                    "y:y:y:y:x:x:x:x/kmj",
                                    "y:y:y:y:x:x:x:x/kzn",
                                    "y:y:y:y:x:x:x:y/j[j]",
                                    "y:y:y:y:x:x:x:y/kmj",
                                    "y:y:y:y:x:x:x:y/kzn",
                                    "y:y:y:y:x:x:y:x/j[j]",
                                    "y:y:y:y:x:x:y:x/kmj",
                                    "y:y:y:y:x:x:y:x/kzn",
                                    "y:y:y:y:x:x:y:y/j[j]",
                                    "y:y:y:y:x:x:y:y/kmj",
                                    "y:y:y:y:x:x:y:y/kzn",
                                    "y:y:y:y:x:y:x:x/j[j]",
                                    "y:y:y:y:x:y:x:x/kmj",
                                    "y:y:y:y:x:y:x:x/kzn",
                                    "y:y:y:y:x:y:x:y/j[j]",
                                    "y:y:y:y:x:y:x:y/kmj",
                                    "y:y:y:y:x:y:x:y/kzn",
                                    "y:y:y:y:x:y:y:x/j[j]",
                                    "y:y:y:y:x:y:y:x/kmj",
                                    "y:y:y:y:x:y:y:x/kzn",
                                    "y:y:y:y:x:y:y:y/j[j]",
                                    "y:y:y:y:x:y:y:y/kmj",
                                    "y:y:y:y:x:y:y:y/kzn",
                                    "y:y:y:y:y:x:x:x/j[j]",
                                    "y:y:y:y:y:x:x:x/kmj",
                                    "y:y:y:y:y:x:x:x/kzn",
                                    "y:y:y:y:y:x:x:y/j[j]",
                                    "y:y:y:y:y:x:x:y/kmj",
                                    "y:y:y:y:y:x:x:y/kzn",
                                    "y:y:y:y:y:x:y:x/j[j]",
                                    "y:y:y:y:y:x:y:x/kmj",
                                    "y:y:y:y:y:x:y:x/kzn",
                                    "y:y:y:y:y:x:y:y/j[j]",
                                    "y:y:y:y:y:x:y:y/kmj",
                                    "y:y:y:y:y:x:y:y/kzn",
                                    "y:y:y:y:y:y:x:x/j[j]",
                                    "y:y:y:y:y:y:x:x/kmj",
                                    "y:y:y:y:y:y:x:x/kzn",
                                    "y:y:y:y:y:y:x:y/j[j]",
                                    "y:y:y:y:y:y:x:y/kmj",
                                    "y:y:y:y:y:y:x:y/kzn",
                                    "y:y:y:y:y:y:y:x/j[j]",
                                    "y:y:y:y:y:y:y:x/kmj",
                                    "y:y:y:y:y:y:y:x/kzn",
                                    "y:y:y:y:y:y:y:y/j[j]",
                                    "y:y:y:y:y:y:y:y/kmj",
                                    "y:y:y:y:y:y:y:y/kzn", ],
                            definitions : {
                                'x' : {
                                    validator : "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]",
                                    cardinality : 4,
                                    prevalidator : [
                                            {
                                                validator : "[0-9a-fA-F]",
                                                cardinality : 1
                                            },
                                            {
                                                validator : "[0-9a-fA-F][0-9a-fA-F]",
                                                cardinality : 2
                                            },
                                            {
                                                validator : "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]",
                                                cardinality : 3
                                            } ]
                                },
                                'y' : {
                                    validator : "[0]",
                                    cardinality : 1
                                },
                                'k' : {
                                    validator : "[1]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'z' : {
                                    validator : "[2]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'n' : {
                                    validator : "[0-8]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'm' : {
                                    validator : "[0-1]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                },
                                'j' : {
                                    validator : "[0-9]",
                                    cardinality : 1,
                                    definitionSymbol : "i"
                                }

                            }
                        }
                    });
})(jQuery);
