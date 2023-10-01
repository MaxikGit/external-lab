'use strict';

/**
 * You must return a date that comes in a predetermined number of seconds after 01.06.2020 00:00:002020
 * @param {number} seconds
 * @returns {Date}
 *
 * @example
 *      31536000 -> 01.06.2021
 *      0 -> 01.06.2020
 *      86400 -> 02.06.2020
 */
function secondsToDate(seconds) {
    const startDate = new Date("2020-06-01");
    const millisecondsToAdd = seconds === undefined ? 0 : seconds * 1000;
    const endDate = new Date(startDate.getTime() + millisecondsToAdd);

    return endDate.toLocaleDateString("ua");
}


/**
 * You must create a function that returns a base 2 (binary) representation of a base 10 (decimal) string number
 * ! Numbers will always be below 1024 (not including 1024)
 * ! You are not able to use parseInt
 * @param {number} decimal
 * @return {string}
 *
 * @example
 *      5 -> "101"
 *      10 -> "1010"
 */
function toBase2Converter(decimal) {
    if (decimal === 0 || decimal === undefined) {
        return "0";
    }
    decimal = Number(decimal);
    let binary = "";
    while (decimal > 0) {
        const remainder = decimal % 2;
        binary = remainder + binary;
        decimal = Math.floor(decimal / 2);
    }

    return binary;
}

/**
 * You must create a function that takes two strings as arguments and returns the number of times the first string
 * is found in the text.
 * @param {string} substring
 * @param {string} text
 * @return {number}
 *
 * @example
 *      'a', 'test it' -> 0
 *      't', 'test it' -> 2
 *      'T', 'test it' -> 2
 */
function substringOccurrencesCounter(substring, text) {
    substring = substring.toLowerCase();
    text = text.toLowerCase();

    let position = text.indexOf(substring);
    let count = 0;
    while (position !== -1) {
        count++;
        position = text.indexOf(substring, position + 1);
    }

    return count;
}

/**
 * You must create a function that takes a string and returns a string in which each character is repeated once.
 *
 * @param {string} string
 * @return {string}
 *
 * @example
 *      "Hello" -> "HHeelloo"
 *      "Hello world" -> "HHeello  wworrldd" // o, l is repeated more then once. Space was also repeated
 */
function repeatingLitters(string) {
    let result = "";

    function wasDoubled(symbol) {
        const firstOccurenceIndex = string.indexOf(symbol);
        if (firstOccurenceIndex === -1) {
            return false;
        }
        return string.indexOf(symbol, firstOccurenceIndex + 1) !== -1;
    }

    for (let i = 0; i < string.length; i++) {

        if (wasDoubled(string[i])) {
            result += string[i];
        }
        else {
            result += string[i] + string[i];
        }
    }

    return result;
}

/**
 * You must write a function redundant that takes in a string str and returns a function that returns str.
 * ! Your function should return a function, not a string.
 *
 * @param {string} str
 * @return {function}
 *
 * @example
 *      const f1 = redundant("apple")
 *      f1() ➞ "apple"
 *
 *      const f2 = redundant("pear")
 *      f2() ➞ "pear"
 *
 *      const f3 = redundant("")
 *      f3() ➞ ""
 */
function redundant(str) {
    return function () {
        return str;
    };
}

/**
 * https://en.wikipedia.org/wiki/Tower_of_Hanoi
 *
 * @param {number} disks
 * @return {number}
 */
function towerHanoi(disks) {
    return (1 << disks) - 1;//same as (2 ** disks) - 1
}

/**
 * You must create a function that multiplies two matricies (n x n each).
 *
 * @param {array} matrix1
 * @param {array} matrix2
 * @return {array}
 *
 */
function matrixMultiplication(matrix1, matrix2) {
    const result = [];

    const n = matrix1.length;
    for (let i = 0; i < n; i++) {
        result.push([]);
        for (let j = 0; j < n; j++) {
            let sum = 0;
            for (let k = 0; k < n; k++) {
                sum += matrix1[i][k] * matrix2[k][j];
            }
            result[i][j] = sum;
        }
    }

    return result;
}

/**
 * Create a gather function that accepts a string argument and returns another function.
 * The function calls should support continued chaining until order is called.
 * order should accept a number as an argument and return another function.
 * The function calls should support continued chaining until get is called.
 * get should return all of the arguments provided to the gather functions as a string in the order specified in the order functions.
 *
 * @param {string} str
 * @return {string}
 *
 * @example
 *      gather("a")("b")("c").order(0)(1)(2).get() ➞ "abc"
 *      gather("a")("b")("c").order(2)(1)(0).get() ➞ "cba"
 *      gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get()  ➞ "hello"
 */
function gather(str) {
    const collected = [str];

    //collecting symbols into array
    function gatherFn(nextStr) {
        collected.push(nextStr);
        return gatherFn;
    }

    //start ordering symbols by incoming indexes
    function order(index) {
        const ordered = [collected[index]];

        function orderSubFn(nextIndex) {
            ordered.push(collected[nextIndex]);
            return orderSubFn;
        }

        orderSubFn.get = function () {
            return ordered.join('');
        };
        //calling next index until get() not called
        return orderSubFn;
    }

    gatherFn.order = order;

    //calling next symbol until order() not called
    return gatherFn;
}