package com.yaps.petstore.server.domain;

import java.util.Collection;

import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.category.CategoryDAO;


public class ListCategories {

    public static void main(String[] args) {
    	CategoryDAO dao = new CategoryDAO();

        // Retrieve all categories from the database
        Collection<Category> categories = null;
		try {
			categories = dao.findAll();
		} catch (ObjectNotFoundException e) {
			System.out.println("The database is empty. You should populate it using :");
			System.out.println(" ant yaps-create-db");
			System.out.println(" ant yaps-insert-data");
			System.exit(2);
		}
        System.out.println("All categories :");
        System.out.println(categories);
    }
}