package todolistbackend

import grails.testing.gorm.DataTest
import spock.lang.Specification
import spock.lang.Unroll

class TarefaSpec extends Specification implements DataTest {

    Class[] getDomainClassesToMock() {
        [Tarefa, Categoria] as Class[]
    }

    void "nova tarefa deve iniciar com status TODO"() {
        expect:
        new Tarefa(prioridade: 3).status == Status.TODO
    }

    @Unroll
    void "prioridade #prioridade deve retornar #resultado"() {
        expect:
        new Tarefa(prioridade: prioridade).validate() == resultado

        where:
        prioridade | resultado
        0          | false
        1          | true
        2          | true
        3          | true
        4          | true
        5          | true
        6          | false
        null       | false
    }
}