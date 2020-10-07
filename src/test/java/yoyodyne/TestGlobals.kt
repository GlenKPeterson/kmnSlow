package yoyodyne

import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout
import yoyodyne.foo.FooBar
import yoyodyne.foo.util.HibernateUtil.*
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

object TestGlobals {

    private val initialized: Boolean by lazy {

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

        connectionDriverClass = "org.h2.Driver"

        connectionUrl = "jdbc:h2:mem:bar"

        val factory: EntityManagerFactory = try {
            val props = mapOf(

                    "hibernate.connection.url" to "jdbc:h2:mem:bar",

                    "hibernate.connection.useUnicode" to "true",
                    "hibernate.connection.characterEncoding" to "UTF-8",
                    "hibernate.hbm2ddl.auto" to "validate",

                    "hibernate.dialect" to "org.hibernate.dialect.H2Dialect",

                    "hibernate.c3p0.max_size" to "60",

                    "hibernate.c3p0.timeout" to "599",
                    "hibernate.bytecode.use_reflection_optimizer" to "false",
                    "hibernate.current_session_context_class" to "thread",
                    "hibernate.format_sql" to "true",
                    "hibernate.max_fetch_depth" to "3",

                    "javax.persistence.schema-generation.database.action" to "drop-and-create",

                    "javax.persistence.sharedCache.mode" to "NONE",
                    "javax.persistence.validation.mode" to "NONE")

            val tempFactory: EntityManagerFactory = Persistence.createEntityManagerFactory(
                    "yoyodyne.foo.persistence",
                    props)

            println("H2 EntityManager Initialized")

            tempFactory
        } catch (ex:Throwable) {

            println("\n==============================================================================")
            println("ERROR: Initial JPA entityManagerFactory creation failed:\n$ex")
            println("\n==============================================================================")
            println("Caused by:\n" + ex.cause)
            println("==============================================================================\n")
            throw ex
        }

        entityManagerFactory.set(factory)
        commit()

        true
    }

    @JvmStatic
    fun testDbInMemory() {
        if (!initialized) {
            throw IllegalStateException("Couldn't initialize Hibernate")
        }
    }

}
