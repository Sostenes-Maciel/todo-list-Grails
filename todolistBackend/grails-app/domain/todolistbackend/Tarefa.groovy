package todolistbackend

import java.time.LocalDate
import java.time.LocalDateTime

class Tarefa {

    String nome
    String descricao
    LocalDate dataTermino
    Integer prioridade
    Categoria categoria
    Status status = Status.TODO
    LocalDateTime horarioAlarme

    static constraints = {
        nome nullable: true
        descricao nullable: true
        dataTermino nullable: true

        prioridade nullable: false, range: 1..5

        categoria nullable: true
        status nullable: false
        horarioAlarme nullable: true
    }

    static mapping = {
        status column: 'status', enumType: 'string'
    }
}