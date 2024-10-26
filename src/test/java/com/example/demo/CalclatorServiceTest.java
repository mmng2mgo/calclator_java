package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

//execute(String action, String left, String right, String currentNumber, String previousValue, String previousOperator, String previousRight)
class CalclatorServiceTest {
	private CalclatorService calclator;
	@Test
	void 初期数値入力() {
		calclator = new CalclatorService();
		Map<String, String> results = calclator.execute("5", "", "", "", "", "", "");
		assertThat(results.get("currentNumber"), is("5"));
	}

	@Test
	void 二桁以上の数値を入れた時のテスト() {
		calclator = new CalclatorService();
		Map<String, String> results = calclator.execute("3", "", "", "2", "2", "", "");
		assertThat(results.get("currentNumber"), is("23"));
	}
	@Test
	void previousValueが数値でない時のテスト() {
		calclator = new CalclatorService();
		Map<String, String> results = calclator.execute("8", "15", "", "15", "+", "+", "+");
		assertThat(results.get("currentNumber"), is("8"));
	}
	@Test
	void operatorを押した時にleftに値が入るかのテスト() {
		calclator = new CalclatorService();
		Map<String, String> results = calclator.execute("+", "", "", "237", "7", "", "");
		assertThat(results.get("left"), is("237"));
	}
	@Test
	void 連続して計算する時のテスト() {
		calclator = new CalclatorService();
		Map<String, String> results = calclator.execute("+", "6", "", "12", "2", "+", "");
		assertThat(results.get("currentNumber"), is("18"));
	}
	@Test
	void 足し算のテスト() {
		calclator = new CalclatorService();
		Map<String, String> results = calclator.execute("=", "6", "", "12", "2", "+", "");
		assertThat(results.get("currentNumber"), is("18"));
	}
	@Test
	void 左辺が入力されていない時のテスト() {
		calclator = new CalclatorService();
		Map<String, String> results = calclator.execute("+", "", "", "", "", "", "");
		assertThat(results.get("currentNumber"), is("0"));
	}
	@Test
	void イコールボタンを連続で押した時のテスト() {
		calclator = new CalclatorService();
		Map<String, String> results = calclator.execute("=", "6", "3", "6", "=", "+", "3");
		assertThat(results.get("currentNumber"), is("9"));
	}

//	}
}
