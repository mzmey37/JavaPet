package com.maksim.javafun;

import com.maksim.javafun.model.Row;
import com.maksim.javafun.step.RowProcessor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaFunApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void processorWorks() throws Exception{
		RowProcessor rowProcessor = new RowProcessor();
		Row row = new Row("1", "Hi");
		Row processedRow = rowProcessor.process(row);
		Assert.assertEquals("1", processedRow.getKey());
		Assert.assertEquals("hi", processedRow.getValue());
	}

}
