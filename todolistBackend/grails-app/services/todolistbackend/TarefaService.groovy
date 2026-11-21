package todolistbackend

import grails.gorm.transactions.Transactional

@Transactional
class TarefaService {

    List<Tarefa> listar(Map parametros = [:]) {
        Tarefa.list(parametros)
    }


    Tarefa buscar(Long id) {
        if (id == null) {
            return null
        }

        Tarefa.get(id)
    }


    Long contar() {
        Tarefa.count()
    }


    Tarefa salvar(Tarefa tarefa) {
        tarefa.save(flush: true)
    }


    void excluir(Tarefa tarefa) {
        tarefa.delete(flush: true)
    }

    Categoria buscarCategoria(Long id) {
        if (id == null) {
            return null
        }

        Categoria.get(id)
    }
}