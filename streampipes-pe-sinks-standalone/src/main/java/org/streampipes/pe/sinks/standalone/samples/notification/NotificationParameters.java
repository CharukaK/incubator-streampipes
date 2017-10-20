package org.streampipes.pe.sinks.standalone.samples.notification;

import org.streampipes.model.impl.graph.SecInvocation;
import org.streampipes.wrapper.params.binding.EventSinkBindingParams;

public class NotificationParameters extends EventSinkBindingParams {

  private String title;
  private String content;

  public NotificationParameters(SecInvocation graph, String title, String content) {
    super(graph);
    this.title = title;
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}