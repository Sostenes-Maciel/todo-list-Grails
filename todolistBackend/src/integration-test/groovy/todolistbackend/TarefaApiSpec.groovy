package todolistbackend

import grails.testing.mixin.integration.Integration
import org.apache.grails.testing.http.client.HttpClientSupport
import spock.lang.Specification

@Integration
class TarefaApiSpec extends Specification implements HttpClientSupport {

    Long tarefaCriadaId

    def cleanup() {
        if (tarefaCriadaId != null) {
            httpDelete("/api/tarefas/${tarefaCriadaId}")
        }
    }

    void "deve executar o CRUD completo pela API REST"() {
        given:
        String nome = "Tarefa automatizada ${UUID.randomUUID()}"

        when:
        def criacao = httpPostJson('/api/tarefas', [
                nome: nome,
                descricao: 'Criada pelo teste automatizado',
                dataTermino: '2026-12-10',
                prioridade: 3,
                status: 'TODO'
        ])

        then:
        criacao.assertStatus(201)

        when:
        tarefaCriadaId = criacao.json().id as Long

        then:
        tarefaCriadaId != null

        when:
        def listagem = http('/api/tarefas')

        then:
        listagem.assertStatus(200)
        listagem.jsonList().any { it.nome == nome }

        when:
        def consulta = http("/api/tarefas/${tarefaCriadaId}")

        then:
        consulta.assertStatus(200)
        consulta.assertJsonContains([nome: nome])

        when:
        def atualizacao = httpPutJson(
                "/api/tarefas/${tarefaCriadaId}",
                [
                        nome: 'Tarefa atualizada pelo teste',
                        descricao: 'Descrição modificada',
                        dataTermino: '2026-12-15',
                        prioridade: 4,
                        status: 'DOING'
                ]
        )

        then:
        atualizacao.assertStatus(200)
        atualizacao.assertJsonContains([
                nome: 'Tarefa atualizada pelo teste',
                prioridade: 4
        ])

        when:
        def exclusao = httpDelete("/api/tarefas/${tarefaCriadaId}")

        then:
        exclusao.assertStatus(204)

        when:
        tarefaCriadaId = null
        def consultaExclusao = http("/api/tarefas/${criacao.json().id}")

        then:
        consultaExclusao.assertStatus(404)
    }

    void "deve rejeitar tarefa com prioridade inválida"() {
        when:
        def resposta = httpPostJson('/api/tarefas', [
                nome: 'Tarefa inválida',
                prioridade: 8
        ])

        then:
        resposta.assertStatus(422)
    }

    void "deve retornar 404 para tarefa inexistente"() {
        when:
        def resposta = http('/api/tarefas/999999999')

        then:
        resposta.assertStatus(404)
    }
}