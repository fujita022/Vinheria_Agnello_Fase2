package dao;

import model.CartItem;
import model.Order;
import model.OrderItem;
import utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int createOrder(int userId, List<CartItem> cartItems, double total) {

        String sqlOrder = "INSERT INTO ORDERS (user_id, total, status) VALUES (?, ?, 'PENDENTE')";
        String sqlItem  = "INSERT INTO ORDER_ITEMS (order_id, wine_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // inicia transação

            // 1. Insere o cabeçalho do pedido
            int orderId;
            try (PreparedStatement stmtOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                stmtOrder.setInt(1, userId);
                stmtOrder.setDouble(2, total);
                stmtOrder.executeUpdate();

                try (ResultSet keys = stmtOrder.getGeneratedKeys()) {
                    if (!keys.next()) throw new RuntimeException("Falha ao obter ID do pedido gerado.");
                    orderId = keys.getInt(1);
                }
            }

            // 2. Insere cada item do carrinho
            try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
                for (CartItem item : cartItems) {
                    stmtItem.setInt(1, orderId);
                    stmtItem.setInt(2, item.getWine().getId());
                    stmtItem.setInt(3, item.getQuantity());
                    stmtItem.setDouble(4, item.getWine().getPrice());
                    stmtItem.addBatch();
                }
                stmtItem.executeBatch();
            }

            conn.commit();
            return orderId;

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Erro ao criar pedido", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public List<Order> findByUser(int userId) {

        String sqlOrders = "SELECT id, user_id, total, status, created_at FROM ORDERS WHERE user_id = ? ORDER BY created_at DESC";
        String sqlItems  = "SELECT oi.id, oi.wine_id, w.name AS wine_name, oi.quantity, oi.unit_price "
                + "FROM ORDER_ITEMS oi "
                + "JOIN WINE w ON w.id = oi.wine_id "
                + "WHERE oi.order_id = ?";

        List<Order> orders = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmtOrders = conn.prepareStatement(sqlOrders)) {

            stmtOrders.setInt(1, userId);

            try (ResultSet rsOrders = stmtOrders.executeQuery()) {
                while (rsOrders.next()) {
                    Order order = new Order();
                    order.setId(rsOrders.getInt("id"));
                    order.setUserId(rsOrders.getInt("user_id"));
                    order.setTotal(rsOrders.getDouble("total"));
                    order.setStatus(rsOrders.getString("status"));
                    order.setCreatedAt(rsOrders.getTimestamp("created_at"));

                    // Busca itens deste pedido
                    try (PreparedStatement stmtItems = conn.prepareStatement(sqlItems)) {
                        stmtItems.setInt(1, order.getId());
                        try (ResultSet rsItems = stmtItems.executeQuery()) {
                            List<OrderItem> items = new ArrayList<>();
                            while (rsItems.next()) {
                                OrderItem item = new OrderItem();
                                item.setId(rsItems.getInt("id"));
                                item.setOrderId(order.getId());
                                item.setWineId(rsItems.getInt("wine_id"));
                                item.setWineName(rsItems.getString("wine_name"));
                                item.setQuantity(rsItems.getInt("quantity"));
                                item.setUnitPrice(rsItems.getDouble("unit_price"));
                                items.add(item);
                            }
                            order.setItems(items);
                        }
                    }
                    orders.add(order);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedidos do usuário", e);
        }

        return orders;
    }

    public Order findById(int orderId, int userId) {

        String sql = "SELECT id, user_id, total, status, created_at FROM ORDERS WHERE id = ? AND user_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotal(rs.getDouble("total"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    return order;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido por ID", e);
        }

        return null;
    }
}