/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module com.camackenzie.exvi.core {
    requires com.google.gson;
    requires java.net.http;

    exports com.camackenzie.exvi.core.api;
    exports com.camackenzie.exvi.core.async;
    exports com.camackenzie.exvi.core.model;
    exports com.camackenzie.exvi.core.util;

    opens com.camackenzie.exvi.core.model;
    opens com.camackenzie.exvi.core.api;
    opens com.camackenzie.exvi.core.async;
    opens com.camackenzie.exvi.core.util;
}
