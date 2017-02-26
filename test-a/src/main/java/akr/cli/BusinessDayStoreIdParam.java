package akr.cli;

import java.util.List;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

public class BusinessDayStoreIdParam implements IValueValidator<List<String>>{

	@Override
	public void validate(String name, List<String> value) throws ParameterException {
		if(value.size() != 2) {
			throw new ParameterException("Excpected two parameters.");
		}	
	}
}
