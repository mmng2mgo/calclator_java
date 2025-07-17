package com.example.demo;

import java.util.HashMap;
import java.util.Map;


public class CalclatorService {
	private String resLeft = "";
	
	private String resRight = "";
	
	private String resCurrentNumber = "";
	
	private String resPreviousValue = "";
	//消す
	private String resPreviousOperator = "";
	
	private String resPreviousRight = "";
	
	
	Map<String, String> numbers = new HashMap<String, String>() {
		{
			put("ZERO", "0");
			put("ONE", "1");
			put("TWO", "2");
			put("THREE", "3");
			put("FOUR", "4");
			put("FIVE", "5");
			put("SIX", "6");
			put("SEVEN", "7");
			put("EIGHT", "8");
			put("NINE", "9");
		}
	};
	Map<String, String> operators = new HashMap<String, String>(){
		{
			put("PLUS", "+");
			put("MINUS", "-");
			put("MULTIPLY", "×");
			put("DIVIDED", "÷");
		}
	};
	public Map<String, String> execute(
			String action,
			String left,
			String right,
			String currentNumber,
			String previousValue,
			String previousOperator,
			String previousRight
			){
		if(numbers.containsValue(action)) {
			Map<String, String>resultNumber = setNumber(action, left, right, currentNumber, previousValue, previousOperator, previousRight);
			resLeft = resultNumber.get("left");
			resRight = resultNumber.get("right");
			resCurrentNumber = resultNumber.get("currentNumber");
			resPreviousValue = resultNumber.get("previousValue");
			resPreviousOperator = resultNumber.get("previousOperator");
			
		}else if(operators.containsValue(action)) {
			Map<String, String>resultOperator = setOperator(action, left, right, currentNumber, previousValue, previousOperator, previousRight);
			resLeft = resultOperator.get("left");
			resRight = resultOperator.get("right");
			resCurrentNumber = resultOperator.get("currentNumber");
			resPreviousValue = resultOperator.get("previousValue");
			resPreviousOperator = resultOperator.get("previousOperator");
		}else if("=".equals(action)) {
			Map<String, String>resultEqual = setEqual(action, left, right, currentNumber, previousValue, previousOperator, previousRight);
			resLeft = resultEqual.get("left");
			resRight = resultEqual.get("right");
			resCurrentNumber = resultEqual.get("currentNumber");
			resPreviousValue = resultEqual.get("previousValue");
			resPreviousRight = resultEqual.get("previousRight");
		}else if("C".equals(action)) {
			Map<String, String>resultCancel = setCancel(action, left, right, currentNumber, previousValue, previousOperator, previousRight);
			resLeft = resultCancel.get("left");
			resRight = resultCancel.get("right");
			resCurrentNumber = resultCancel.get("currentNumber");
			resPreviousValue = resultCancel.get("previousValue");
			resPreviousRight = resultCancel.get("previousRight");
		}
		Map<String, String> results = new HashMap<String, String>(){
			{
				put("left", resLeft);
				put("right", resRight);
				put("currentNumber", resCurrentNumber);
				put("previousValue", resPreviousValue);
				put("previousOperator", resPreviousOperator);
				put("previousRight", resPreviousRight);
			}
		};
		return results;
	}
//numberは入力値、その他は状態を持つ変数,currentNumberは表示する値,previousValueは一つ前の値
//moreNumbersは不要 currentNumber = currentNumber + numberで良い
	public Map<String, String> setNumber(
			String number,
			String left,
			String right,
			String currentNumber,
			String previousValue,
			String previousOperator,
			String previousRight
			) {
		//| previousValue == "を削除
		if (numbers.containsValue(previousValue)) {
			resCurrentNumber += currentNumber + number;
		}else {
		//previousValueが数値でない場合
			resCurrentNumber = number;
		}
		resPreviousValue = number;
		
		resLeft = left;
		resRight = right;
		resPreviousOperator = previousOperator;
		
		Map<String, String> results = new HashMap<String, String>(){
			{
				put("left", resLeft);
				put("right", resRight);
				put("currentNumber", resCurrentNumber);
				put("previousValue", resPreviousValue);
				put("previousOperator", resPreviousOperator);
				put("previousRight", resPreviousRight);
			}
		};
		return results;
 	}
	
	//moreNumbersではなくてCurrentNumber。CurrentNumberは数値のみを受け付けるロジックにしてある
	public Map<String, String> setOperator(
			String operator,
			String left,
			String right,
			String currentNumber,
			String previousValue,
			String previousOperator,
			String previousRight
			){
		if(left == "") {
			resLeft = currentNumber.isEmpty() ? "0" : currentNumber;
			resCurrentNumber = resLeft;
		}else if(right == ""){
			resLeft = left;
			resRight = currentNumber.isEmpty() ? "0" : currentNumber;
		}
		
		if(previousOperator != "") {
			//21+21=+を押すと表示が0になる
			if(previousValue.equals("=")) {
				resCurrentNumber = currentNumber.isEmpty() ? left : currentNumber;
				
			}
			//previousOperatorごとで条件分岐
			int leftNumber = Integer.parseInt(resLeft);
			int rightNumber = Integer.parseInt(resRight);
			
			Map<String, String> calcResult = calc(previousOperator, leftNumber, rightNumber);
			resCurrentNumber = calcResult.get("currentNumber");
			resLeft = calcResult.get("left");
			resRight = calcResult.get("right");
		}
		
		resPreviousOperator = operator;
		resPreviousValue = operator;
		
		Map<String, String> results = new HashMap<String, String>(){
			{
				put("left", resLeft);
				put("right", resRight);
				put("currentNumber", resCurrentNumber);
				put("previousValue", resPreviousValue);
				put("previousOperator", resPreviousOperator);
				put("previousRight", resPreviousRight);
			}
		};
		return results;
	}
	public Map<String, String> setEqual(
			String equal,
			String left,
			String right,
			String currentNumber,
			String previousValue,
			String previousOperator,
			String previousRight
			){
		//+3===
		if(left == "") {
			resLeft = currentNumber.isEmpty() ? "0" : currentNumber;
		}else if(right == ""){
			resLeft = left;
			resRight = currentNumber.isEmpty() ? "0" : currentNumber;
		}
		// モデルに left と right をセット		
		
		if(previousValue.equals("=")) {
			resLeft = left;
			previousRight = right;
			int previousEqualLeftNumber = Integer.parseInt(resLeft);
			int previousRightNumber = Integer.parseInt(previousRight);
			Map<String, String> calcEqual = calc(previousOperator, previousEqualLeftNumber, previousRightNumber);
			resCurrentNumber = calcEqual.get("currentNumber");
			resLeft = calcEqual.get("left");
			resRight = calcEqual.get("right");
			resPreviousValue = equal;
			Map<String, String> results = new HashMap<String, String>(){
				{
					put("left", resLeft);
					put("right", resRight);
					put("currentNumber", resCurrentNumber);
					put("previousValue", resPreviousValue);
					put("previousRight", resPreviousRight);
				}
			};
			return results;
		}
		
		int leftNumber = Integer.parseInt(resLeft);
		int rightNumber = Integer.parseInt(resRight);
		Map<String, String> calcResult = calc(previousOperator, leftNumber, rightNumber);
		resCurrentNumber = calcResult.get("currentNumber");
		resLeft = calcResult.get("left");
		resRight = calcResult.get("right");
		
		resPreviousValue = equal;
		Map<String, String> results = new HashMap<String, String>(){
			{
				put("left", resLeft);
				put("right", resRight);
				put("currentNumber", resCurrentNumber);
				put("previousValue", resPreviousValue);
				put("previousRight", resPreviousRight);
			}
		};
		return results;
	}
	public Map<String, String> setCancel(
			String cancel,
			String left,
			String right,
			String currentNumber,
			String previousValue,
			String previousOperator,
			String previousRight		
	){
		resLeft = "";
		resRight = "";
		resCurrentNumber = "0";
		resPreviousValue = "";
		resPreviousOperator = "";
		resPreviousRight = "";
		
		Map<String, String> results = new HashMap<String, String>(){
			{
				put("left", resLeft);
				put("right", resRight);
				put("currentNumber", resCurrentNumber);
				put("previousValue", resPreviousValue);
				put("previousRight", resPreviousRight);
			}
		};
		return results;
	}
	//実際の計算
	public Map<String, String> calc(String previousOperator, int leftNumber, int rightNumber) {
		String plus = operators.get("PLUS");
		String minus = operators.get("MINUS");
		String multiply = operators.get("MULTIPLY");
		String divided = operators.get("DIVIDED");
		if (previousOperator.equals(plus)) {
			int result = plus(leftNumber, rightNumber);
			resLeft = Integer.toString(result);
			resCurrentNumber = Integer.toString(result); 
			resRight = "";
		}else if(previousOperator.equals(minus)) {
			int result = minus(leftNumber, rightNumber);
			resLeft = Integer.toString(result);
			resCurrentNumber = Integer.toString(result);
			resRight = "";
		}else if(previousOperator.equals(multiply)) {
			int result = multiply(leftNumber, rightNumber);
			resLeft = Integer.toString(result);
			resCurrentNumber = Integer.toString(result); 
			resRight = "";
		}else if(previousOperator.equals(divided)) {
			int result = divided(leftNumber, rightNumber);
			resLeft = Integer.toString(result);
			resCurrentNumber = Integer.toString(result); 
			resRight = "";
		}else if(previousOperator == "-") {
			int result = leftNumber - rightNumber;
			resLeft = Integer.toString(result);
			resCurrentNumber = Integer.toString(result);
			resRight = "";
		}
		Map<String, String> results = new HashMap<>();
		results.put("currentNumber", resCurrentNumber);
		results.put("left", resLeft);
		results.put("right", resRight);
		return results;
	}
	//四則演算
	private int plus(int left, int right) {
		return left + right;
	}
	private int minus(int left, int right) {
		return left - right;
	}
	private int multiply(int left, int right) {
		return left * right;
	}
	private int divided(int left, int right) {
		return left / right;
	}
}    


