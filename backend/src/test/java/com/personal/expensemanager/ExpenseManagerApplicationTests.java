package com.personal.expensemanager;

import com.personal.expensemanager.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@SpringBootTest
@Suite.SuiteClasses({
		AccountServiceTest.class,
		CategoryServiceTest.class,
		SubCategoryServiceTest.class,
		TransactionServiceTest.class
})

public class ExpenseManagerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
