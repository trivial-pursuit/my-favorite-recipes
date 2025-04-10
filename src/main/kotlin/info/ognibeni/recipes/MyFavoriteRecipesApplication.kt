package info.ognibeni.recipes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MyFavoriteRecipesApplication

fun main(args: Array<String>) {
	runApplication<MyFavoriteRecipesApplication>(*args)
}
