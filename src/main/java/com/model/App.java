/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/*Para hacer la conexion por anotaciones*/
import com.config.SpringMongoConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/*Para hacer la conexion por archivo XML
import org.springframework.context.support.GenericXmlApplicationContext;*/
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 *
 * @author Franck
 */
public class App {
    public static void main(String[] args) {

		// Por XML, archivo en resourse/SpringConfig
		//ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");

		// Por Annotation
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
                
                //User user = new User("admin", "admin123");
		// Guardar
		mongoOperation.save(new User("juan", "admin123"));

		// now user object got the created id.
		//System.out.println("1. user : " + user);

		// Crea la consulta de busqueda
		Query searchUserQuery = new Query(Criteria.where("username").is("juan"));

		// Busca en la BD.
		User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
		System.out.println("2. find - savedUser : " + savedUser);

		// Actualizar password
		mongoOperation.updateFirst(searchUserQuery, Update.update("password", "new password"),
				User.class);

		// find the updated user object
		User updatedUser = mongoOperation.findOne(
				new Query(Criteria.where("username").is("juan")), User.class);

		System.out.println("3. updatedUser : " + updatedUser);

		// Borra los datos
		//mongoOperation.remove(searchUserQuery, User.class);

		// crea una lista para contar
		List<User> listUser = mongoOperation.findAll(User.class);
		System.out.println("4. Number of user = " + listUser.size());

	}

}