package akr.cli;

import java.io.File;
import java.util.List;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

public class BeustJCommanderMain {

	public static void main(String[] args) {
		
		JCommander jc = new JCommander();
        CmdParams pars = new CmdParams();
        jc.addObject(pars);
        
        try {
            jc.parse(args);
            System.out.println(pars.configFile);
            System.out.println(pars.xOption);
            System.out.println(pars.nonOptions);
        } catch (ParameterException pe) {
            System.out.println(pe.getClass().getSimpleName() + ": " + pe.getMessage());
            jc.usage();
        }
    }
}

@Parameters(optionPrefixes = "-")
class CmdParams {
	
	public static class PropFileParam implements IStringConverter<File>, IParameterValidator {

		@Override
		public void validate(String name, String value) throws ParameterException {
			if ("-c".equals(name) && null != value && value.matches("^.*\\.properties$")) {

				final File file = convert(value);
				if (null == file || !file.exists()) {
					throw new ParameterException(String.format("File %s not a property file or it doesn't exist."));
				}
			} else {
				throw new ParameterException(String.format("File %s not a property file or it doesn't exist.", value));
			}
		}

		@Override
		public File convert(String value) {
			return new File(PropFileParam.class.getClassLoader().getResource("config.properties").getFile());
		}
	}
	
	@Parameter(names = "-x", required = false)
    public Boolean xOption = false;
	
	@Parameter(names = "-c", arity = 1, description = "Spark job configuration", required = true, validateWith = PropFileParam.class, converter = PropFileParam.class)
    public File configFile;
	
	// no support for validation class
	@Parameter(description = "businessday [storyid]", required=true)
	public List<String> nonOptions;
}

