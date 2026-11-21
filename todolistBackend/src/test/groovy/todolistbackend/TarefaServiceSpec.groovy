package todolistbackend

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class TarefaServiceSpec extends Specification
        implements ServiceUnitTest<TarefaService>, DataTest {

    Class[] getDomainClassesToMock() {
        [Tarefa, Categoria] as Class[]
    }

    void "deve salvar e buscar uma tarefa"() {
        when:
        Tarefa criada = service.salvar(
                new Tarefa(nome: 'Estudar Grails', prioridade: 3)
        )

        then:
        criada.id != null
        service.buscar(criada.id)?.nome == 'Estudar Grails'
    }

    void "deve listar e contar tarefas"() {
        given:
        service.salvar(new Tarefa(nome: 'Tarefa 1', prioridade: 2))
        service.salvar(new Tarefa(nome: 'Tarefa 2', prioridade: 4))

        expect:
        service.listar().size() == 2
        service.contar() == 2
    }

    void "deve excluir uma tarefa"() {
        given:
        Tarefa criada = service.salvar(
                new Tarefa(nome: 'Tarefa temporária', prioridade: 1)
        )

        when:
        service.excluir(criada)

        then:
        service.buscar(criada.id) == null
    }

    void "deve buscar categoria pelo identificador"() {
        given:
        Categoria categoria = new Categoria(nome: 'Trabalho').save(flush: true)

        expect:
        service.buscarCategoria(categoria.id)?.nome == 'Trabalho'
    }
}