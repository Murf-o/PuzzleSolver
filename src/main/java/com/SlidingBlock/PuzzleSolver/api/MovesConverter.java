package com.SlidingBlock.PuzzleSolver.api;

import java.util.ArrayList;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MovesConverter implements AttributeConverter<ArrayList<String>, String>{

	@Override
	public String convertToDatabaseColumn(ArrayList<String> attribute) {
		// TODO Auto-generated method stub
		return attribute.toString();
	}

	@Override
	public ArrayList<String> convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		ArrayList<String> ret = new ArrayList<String>();
		ret.add(dbData);
		return ret;
	}

}
