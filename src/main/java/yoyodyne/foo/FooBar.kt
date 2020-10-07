package yoyodyne.foo

import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout
import yoyodyne.foo.util.HibernateUtil.*
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

object FooBar {

    @JvmField
    val logger: Logger = Logger.getLogger(FooBar::class.java)
}

fun main() {
    val root = Logger.getRootLogger()

    val conversionPattern = "%d{yyyyMMdd HH:mm:ss} [%.2t] %p %c{1}: %m%n"

    val ca = ConsoleAppender(PatternLayout(conversionPattern))
    ca.name = "C1"
    ca.encoding = "UTF-8"
    ca.threshold = Level.INFO
    root.addAppender(ca)

    Logger.getLogger("com.mchange.v2").level = Level.WARN
    Logger.getLogger("org.hibernate").level = Level.ERROR

    Logger.getLogger("org.eclipse.jetty").level = Level.INFO
    Logger.getLogger("org.hibernate.SQL").level = Level.INFO
    Logger.getLogger("planbase").level = Level.INFO

    val logger = FooBar.logger
    logger.info("Log4j Initialization completed.")

    try {

        val factory: EntityManagerFactory = try {
            val props: MutableMap<String,String> =
                    mutableMapOf("hibernate.connection.driver_class" to connectionDriverClass,
                                 "hibernate.connection.url" to connectionUrl,
                                 "hibernate.connection.username" to connectionUserName,
                                 "hibernate.connection.password" to connectionPassword,
                                 "hibernate.connection.useUnicode" to "true",
                                 "hibernate.connection.characterEncoding" to "UTF-8",
                                 "hibernate.hbm2ddl.auto" to "validate",

                                 "hibernate.dialect.storage_engine" to "innodb",

                                 "hibernate.c3p0.max_size" to "60",

                                 "hibernate.c3p0.timeout" to "599",
                                 "hibernate.bytecode.use_reflection_optimizer" to "false",
                                 "hibernate.current_session_context_class" to "thread",
                                 "hibernate.format_sql" to "true",
                                 "hibernate.max_fetch_depth" to "3",

                                 "javax.persistence.sharedCache.mode" to "NONE",
                                 "javax.persistence.validation.mode" to "NONE")

            props["javax.persistence.schema-generation.scripts.action"] = "drop-and-create"

            val tempFactory: EntityManagerFactory =
                    Persistence.createEntityManagerFactory(
                            "yoyodyne.foo.persistence",
                            props)

            tempFactory
        } catch (ex: Throwable) {

            System.err.println("\n==============================================================================")
            System.err.println("ERROR: Initial JPA entityManagerFactory creation failed:\n$ex")
            System.err.println("\n==============================================================================")
            System.err.println("Caused by:\n${ex.cause}")
            System.err.println("==============================================================================\n")
            throw ex
        }

        entityManagerFactory.set(factory)
        commit()

        quit()
    } catch (t: Throwable) {

        println("Problem getting JPA EntityManager: $t")
        t.printStackTrace()
    }
}