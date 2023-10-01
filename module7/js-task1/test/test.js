describe("secondsToDate", function () {
    // *      31536000 -> 01.06.2021
    // *      0 -> 01.06.2020
    // *      86400 -> 02.06.2020
    it("31536000 seconds => 01.06.2021", function () {
        assert.equal(secondsToDate(31536000), "01.06.2021");
    });

    it("0 seconds or () => 01.06.2020", function () {
        assert.equal(secondsToDate(0), "01.06.2020");
        assert.equal(secondsToDate(), "01.06.2020");
    });

    it("86400 seconds => 02.06.2020", function () {
        assert.equal(secondsToDate(86400), "02.06.2020");
    });
});

describe("toBase2Converter", function () {
    // *      5 -> "101"
    // *      10 -> "1010"
    it("5 in binary => 101", function () {
        assert.equal(toBase2Converter(5), "101");
    });

    it("10 in binary => 1010", function () {
        assert.equal(toBase2Converter(10), "1010");
    });

    it("0 or () in binary => 0", function () {
        assert.equal(toBase2Converter(0), "0");
    });
});

describe("substringOccurrencesCounter", function () {
    // *      'a', 'test it' -> 0
    // *      't', 'test it' -> 3
    // *      'T', 'test it' -> 3
    it("occurence 'a' in 'test it' => 0", function () {
        assert.equal(substringOccurrencesCounter('a', 'test it'), 0);
    });

    it("occurence 't' in 'test it' => 3", function () {
        assert.equal(substringOccurrencesCounter('t', 'test it'), 3);
    });

    it("occurence 'T' in 'test it' => 3", function () {
        assert.equal(substringOccurrencesCounter('T', 'test it'), 3);
    });
});

describe("repeatingLitters", function () {
    // *      "Hello" -> "HHeelloo"
    // *      "Hello world" -> "HHeello  wworrldd" // o, l is repeated more then once. Space was also repeated
    it("Hello  => HHeelloo", function () {
        assert.equal(repeatingLitters("Hello"), "HHeelloo");
    });

    it("Hello world => HHeello  wworrldd", function () {
        assert.equal(repeatingLitters("Hello world"), "HHeello  wworrldd");
    });

});

describe("redundant", function () {
    // *      const f1 = redundant("apple")
    // *      f1() ➞ "apple"
    // *
    // *      const f2 = redundant("pear")
    // *      f2() ➞ "pear"
    // *
    // *      const f3 = redundant("")
    // *      f3() ➞ ""
    it("const f1 = redundant('apple'); f1() ➞ apple", function () {
        const f1 = redundant("apple");
        assert.equal(f1(), "apple");
    });

    it("const f1 = redundant('pear'); f1() ➞ pear", function () {
        const f1 = redundant("pear");
        assert.equal(f1(), "pear");
    });
    it("const f3 = redundant(''); f1() ➞ ''", function () {
        const f1 = redundant("");
        assert.equal(f1(), "");
    });
});

describe("gather", function () {
    // *      gather("a")("b")("c").order(0)(1)(2).get() ➞ "abc"
    // *      gather("a")("b")("c").order(2)(0)(1).get() ➞ "cab"
    // *      gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get()  ➞ "hello"
    it("gather(\"a\")(\"b\")(\"c\").order(0)(1)(2).get() ➞ \"abc\"", function () {
        assert.equal(gather("a")("b")("c").order(0)(1)(2).get(), "abc");
    });

    it("gather(\"a\")(\"b\")(\"c\").order(2)(0)(1).get() ➞ \"abc\"", function () {
        assert.equal(gather("a")("b")("c").order(2)(0)(1).get(), "cab");
    });

    it("gather(\"e\")(\"l\")(\"o\")(\"l\")(\"!\")(\"h\").order(5)(0)(1)(3)(2)(4).get()  ➞ \"hello!\"", function () {
        assert.equal(gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get(), "hello!");
    });
});