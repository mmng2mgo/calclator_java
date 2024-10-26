package com.example.demo;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class CalcController {
	
	//actionを元に条件分岐する
	//left、right、previousOperatorを引数にとる
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	@PostMapping("/calclator")
	public String useCalcService(
			@RequestParam("action") String action,
			@RequestParam("left") String left,
			@RequestParam("right") String right,
			@RequestParam("currentNumber") String currentNumber,
			@RequestParam("previousValue") String previousValue,
			@RequestParam("previousOperator") String previousOperator,
			@RequestParam("previousRight") String previousRight,
			Model model
			) {
		CalclatorService calclate = new CalclatorService();
		Map<String, String> results = calclate.execute(action, left, right, currentNumber, previousValue, previousOperator, previousRight);
		model.addAttribute("left", results.get("left"));
		model.addAttribute("right", results.get("right"));
		model.addAttribute("currentNumber", results.get("currentNumber"));
		model.addAttribute("previousValue", results.get("previousValue"));
		model.addAttribute("previousOperator", results.get("previousOperator"));
		model.addAttribute("previousRight", results.get("previousRight"));
		return "index";
	}
}