package com.weverson.gestao_pedidos;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Gestão de Pedidos", description = "Operações para consulta, cadastro, alteração e exclusão de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    @Operation(summary = " Listar todos os pedidos", description = "Retorna a lista completa de pedidos cadastrados no banco de dados.")
    public List<Pedido> listarPedidos() {
        return pedidoService.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = " Cadastrar novo pedido", description = "Registra um novo pedido com o nome do cliente e o valor total.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados do pedido inválidos (validação falhou)")
    })
    public Pedido criarPedido(@Valid @RequestBody Pedido pedido) {
        return pedidoService.criar(pedido);
    }

    @GetMapping("/{id}")
    @Operation(summary = " Obter pedido por ID", description = "Busca as informações detalhadas de um pedido específico a partir do seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = " Atualizar pedido existente", description = "Atualiza os dados de um pedido já cadastrado no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados informados são inválidos"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado para atualização")
    })
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @Valid @RequestBody Pedido dados) {
        Optional<Pedido> pedidoAtualizado = pedidoService.atualizar(id, dados);
        return pedidoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = " Remover pedido", description = "Exclui permanentemente o pedido informado do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "24", description = "Pedido removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        if (pedidoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}